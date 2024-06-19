import * as fs from 'fs/promises';
import * as path from 'path';
import {
  ArgumentListCtx,
  BaseJavaCstVisitorWithDefaults,
  BinaryExpressionCtx,
  ClassBodyDeclarationCtx,
  ClassDeclarationCtx,
  ClassMemberDeclarationCtx,
  CstNode,
  ExpressionCtx,
  FieldDeclarationCtx,
  FqnOrRefTypeCtx,
  FqnOrRefTypePartCommonCtx,
  FqnOrRefTypePartFirstCtx,
  ImportDeclarationCtx,
  LambdaBodyCtx,
  LambdaExpressionCtx,
  NewExpressionCtx,
  NormalClassDeclarationCtx,
  PackageDeclarationCtx,
  PrimaryCtx,
  PrimaryPrefixCtx,
  PrimarySuffixCtx,
  TernaryExpressionCtx,
  TypeIdentifierCtx,
  UnannClassOrInterfaceTypeCtx,
  UnannReferenceTypeCtx,
  UnannTypeCtx,
  UnaryExpressionCtx,
  UnqualifiedClassInstanceCreationExpressionCtx,
  VariableDeclaratorCtx,
  VariableDeclaratorIdCtx,
  VariableDeclaratorListCtx,
  VariableInitializerCtx,
  parse,
} from 'java-parser';
import {
  chkBothOf,
  chkFieldType,
  hasFieldType,
  isArray,
  isNonNullable,
  isNumber,
} from '@freik/typechk';
import { MakeStack } from '@freik/containers';
import { removeComments } from './helpers/removeComments.js';

/*** BEGIN CONFIGURATION STUFF ***/
// This is the package name that we're going to use for our generated code.
const packageDir = ['com', 'robotcode', 'shared'];
// This is a set of imports that we want to remove from the generated code.
const removeImports = new Set(['com.acmerobotics.dashboard.config.Config']);
// This is a map of old import names to new ones for the generated code.
const importMap = new Map([
  [
    'com.technototes.path.geometry.ConfigurablePoseD',
    'com.acmerobotics.roadrunner.geometry.Pose2d',
  ],
  [
    'com.technototes.path.geometry.ConfigurablePose',
    'com.acmerobotics.roadrunner.geometry.Pose2d',
  ],
  [
    'com.technototes.path.trajectorysequence.TrajectorySequence',
    'com.acmerobotics.roadrunner.trajectory.Trajectory',
  ],
  [
    'com.technototes.path.trajectorysequence.TrajectorySequenceBuilder',
    'com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder',
  ],
]);
const extraImports = [
  'static java.lang.Math.toRadians',
  'java.util.function.Supplier',
];
const typeMap = new Map([
  ['ConfigurablePose', 'Pose2d'],
  ['ConfigurablePoseD', 'Pose2d'],
  [
    'Function<Function<Pose2d,TrajectorySequenceBuilder>,TrajectorySequence>',
    'Supplier<Trajectory>',
  ],
]);

/*** END CONFIGURATION STUFF ***/

const [, , outDir, className, filesAsString] = process.argv;
const outputLocation = path.join(outDir, 'generated-sources', ...packageDir);

// We're only finding files that include "auto" and "const" in their paths.
const filesNoBrackets = filesAsString.substring(1, filesAsString.length - 1);
const files = filesNoBrackets
  .split(', ')
  .filter(
    (val) =>
      val.toLocaleLowerCase().indexOf('auto') >= 0 &&
      val.toLocaleLowerCase().indexOf('const') >= 0 &&
      val.toLocaleLowerCase().indexOf('meepmeep') < 0,
  );

// A really dumb symbol table (That I'm only filling, but never using...)
const symbolTypes = new Map<string, string>();
let classHelpersEmitted = false;

// Some output state:
const imports = new Set<string>(extraImports);

const theCode: string[] = [];

let prev = '';

function codeSpit(...args: string[]): void {
  if (prev.length > 0) {
    args = [prev, ...args];
    prev = '';
  }
  if (args.length > 1) {
    theCode.push(args.join(''));
  } else {
    theCode.push(args[0]);
  }
}

function codeAdd(...args: string[]): void {
  prev += args.join('');
}

function codeReset() {
  prev = '';
}

function unsupported(name: string, obj: object): void {
  if (obj && obj[name]) {
    throw new Error(`${name} is unsupported`);
  }
}
function required(obj: unknown, message?: string): obj is NonNullable<unknown> {
  if (!obj) {
    throw new Error(message ?? 'Required type not found :(');
  }
  return true;
}
function assert(obj: unknown, message: string): obj is NonNullable<unknown> {
  if (!obj) {
    throw new Error(message);
  }
  return true;
}

let curFile: string = '';

function getCode(start: number, end: number): string {
  return curFile.substring(start, end + 1);
}

function getItemContent(f: unknown): string {
  if (
    hasFieldType(
      f,
      'location',
      chkBothOf(
        chkFieldType('startOffset', isNumber),
        chkFieldType('endOffset', isNumber),
      ),
    )
  ) {
    return getCode(f.location.startOffset, f.location.endOffset);
  }
  return '';
}

function getContent(field: unknown, sep?: string): string {
  return isArray(field)
    ? field.map(getItemContent).join(sep ?? ' ')
    : getItemContent(field);
}

function noWhitespace(input: string): string {
  return input.replace(/\s/g, '');
}

function emitClassHelpers() {
  if (classHelpersEmitted) {
    return;
  }
  classHelpersEmitted = true;
  // public static MinVelocityConstraint MIN_VEL = new MinVelocityConstraint(Arrays.asList(
  //    new AngularVelocityConstraint(60 /*MAX_ANG_VEL*/),
  //    new MecanumVelocityConstraint(60 /*MAX_VEL*/, 14 /*TRACK_WIDTH*/)));
  // public static ProfileAccelerationConstraint PROF_ACCEL = new ProfileAccelerationConstraint(20/*MAX_ACCEL*/);
  codeSpit('public static Function<Pose2d, TrajectoryBuilder> func;'); // = pose -> new TrajectoryBuilder(pose, MIN_VEL, PROF_ACCEL);")`);
}

// A little "context" stack
enum TokenKind {
  NewType,
  LambdaParam,
  DeclType,
}
type Token = { kind: TokenKind; value: string };
const MakeToken = (kind: TokenKind, value: string) => ({ kind, value });
const tokenStack = MakeStack<Token>();

class AutoConstVisitor extends BaseJavaCstVisitorWithDefaults {
  output: string[];
  constructor() {
    super();
    this.output = [];
    this.validateVisitor();
  }

  maybeVisit(field: unknown): void {
    if (!isNonNullable(field)) return;
    this.visit(field as CstNode);
  }

  mustVisit(obj: unknown): void {
    if (isNonNullable(obj)) {
      this.maybeVisit(obj as CstNode);
    } else {
      throw new Error(`Missing required child element`);
    }
  }

  // Rewire the package:
  packageDeclaration(ctx: PackageDeclarationCtx, param?: unknown) {
    codeSpit(
      '// Original package: ',
      ctx.Identifier.map((token) => token.image).join('.'),
    );
    // codeSpit('package ', packageDir.join('.'), ';');
  }

  // Copy, reroute, or remove imports:
  importDeclaration(ctx: ImportDeclarationCtx, param?: unknown) {
    const stat = ctx.Static ? 'static ' : '';
    const star = ctx.Star ? '.*' : '';
    const imprt = ctx.packageOrTypeName
      .map((cst) => cst.children.Identifier.map((tok) => tok.image).join('.'))
      .join('.');
    const actual = importMap.has(imprt) ? importMap.get(imprt) : imprt;
    const key = `${stat}${actual}${star}`;
    imports.add(key);
  }

  classDeclaration(ctx: ClassDeclarationCtx, param?: any) {
    // Class declarations are smashed to just be public static:
    codeAdd('public static class ');
    classHelpersEmitted = false;
    this.mustVisit(ctx.normalClassDeclaration);
  }
  normalClassDeclaration(ctx: NormalClassDeclarationCtx, param?: any) {
    // No extends/implements are carried over
    this.mustVisit(ctx.typeIdentifier);
    codeSpit(' {');
    this.mustVisit(ctx.classBody);
    codeSpit('}');
  }
  // spit out the type identifier
  typeIdentifier(ctx: TypeIdentifierCtx, param?: any) {
    codeAdd(ctx.Identifier.map((tok) => tok.image).join('.'));
  }
  // We're picky about the type of class bodies supported
  classBodyDeclaration(ctx: ClassBodyDeclarationCtx, param?: any) {
    // No constructor!
    unsupported('constructorDeclaration', ctx);
    // No instance initializers!
    unsupported('instanceInitializer', ctx);
    // No static initializers!
    unsupported('staticInitializer', ctx);
    this.mustVisit(ctx.classMemberDeclaration);
  }
  classMemberDeclaration(ctx: ClassMemberDeclarationCtx, param?: any) {
    // We don't support methods!
    unsupported('methodDeclaration', ctx);
    // We don't support interfaces, either.
    unsupported('interfaceDeclaration', ctx);
    this.maybeVisit(ctx.fieldDeclaration);
    this.maybeVisit(ctx.classDeclaration);
  }
  fieldDeclaration(ctx: FieldDeclarationCtx, param?: any) {
    emitClassHelpers();
    // Add the field modifiers
    codeAdd(getContent(ctx.fieldModifier), ' ');
    this.mustVisit(ctx.unannType);
    this.mustVisit(ctx.variableDeclaratorList);
    if (tokenStack.peek()?.kind === TokenKind.DeclType) {
      tokenStack.pop();
    } else {
      // No constants, random variables, whatever. Delete 'em!
      codeReset();
    }
  }
  unannType(ctx: UnannTypeCtx, param?: any) {
    this.maybeVisit(ctx.unannReferenceType);
    if (ctx.unannPrimitiveTypeWithOptionalDimsSuffix) {
      codeAdd(getContent(ctx.unannPrimitiveTypeWithOptionalDimsSuffix));
    }
  }
  unannReferenceType(ctx: UnannReferenceTypeCtx, param?: any) {
    this.mustVisit(ctx.unannClassOrInterfaceType);
  }
  unannClassOrInterfaceType(ctx: UnannClassOrInterfaceTypeCtx, param?: any) {
    const typeName = noWhitespace(getContent(ctx.unannClassType));
    tokenStack.push(MakeToken(TokenKind.DeclType, typeName));
    codeAdd(typeMap.get(typeName) || typeName);
  }
  variableDeclaratorList(ctx: VariableDeclaratorListCtx, param?: any) {
    this.mustVisit(ctx.variableDeclarator);
  }
  variableDeclarator(ctx: VariableDeclaratorCtx, param?: any) {
    this.mustVisit(ctx.variableDeclaratorId);
    this.mustVisit(ctx.variableInitializer);
  }
  variableDeclaratorId(ctx: VariableDeclaratorIdCtx, param?: any) {
    if (required(ctx.Identifier, 'Unsupported Identifier-less varDeclID')) {
      const varName = ctx.Identifier[0].image;
      codeAdd(' ', varName, ' = ');
      // This is setting us up to keep track of variable name types,
      // so we can yoink ".toPose()" modifiers later in the file...
      const declType = tokenStack.peek();
      if (declType?.kind === TokenKind.DeclType) {
        symbolTypes.set(varName, declType.value);
      }
    }
  }
  variableInitializer(ctx: VariableInitializerCtx, param?: any) {
    this.mustVisit(ctx.expression);
  }
  expression(ctx: ExpressionCtx, param?: any) {
    this.maybeVisit(ctx.lambdaExpression);
    this.maybeVisit(ctx.ternaryExpression);
  }
  lambdaExpression(ctx: LambdaExpressionCtx, param?: any) {
    const params = getContent(ctx.lambdaParameters, ', ');
    // We need to transform b -> b.apply(...) to () -> func.apply(...)
    // Push the parameter(s) to the lambda for the lambdaBody to process
    tokenStack.push(MakeToken(TokenKind.LambdaParam, params));
    this.mustVisit(ctx.lambdaBody);
  }
  lambdaBody(ctx: LambdaBodyCtx, param?: any) {
    const expr = noWhitespace(getContent(ctx.expression));
    // console.log('Expr:  ', expr);

    unsupported('block', ctx);
    const top = tokenStack.pop();
    if (
      top &&
      top.kind === TokenKind.LambdaParam &&
      expr.startsWith(`${top.value}.apply(`)
    ) {
      // This is pretty dumb given that we have the full syntax tree,
      // But it's *easy* :D
      const cleanupExpr = expr
        .substring(top.value.length)
        .replaceAll('.toPose()', '');
      codeSpit('() -> ', 'func', cleanupExpr, ';');
    } else if (top) {
      codeSpit(top.value, ' -> ', expr);
    } else {
      throw new Error('Unexpected Lambda body stack');
    }
  }
  // Ternary expression is the container for all non-lambdas
  // which is definitely a little weird, but whatever...
  ternaryExpression(ctx: TernaryExpressionCtx, param?: any) {
    unsupported('QuestionMark', ctx);
    unsupported('Colon', ctx);
    this.mustVisit(ctx.binaryExpression);
  }
  binaryExpression(ctx: BinaryExpressionCtx, param?: any) {
    assert(
      !(
        ctx.AssignmentOperator ||
        ctx.BinaryOperator ||
        ctx.Greater ||
        ctx.Instanceof ||
        ctx.Less ||
        ctx.pattern ||
        ctx.referenceType
      ),
      'unsupported child of binary expression',
    );
    this.maybeVisit(ctx.expression);
    this.mustVisit(ctx.unaryExpression);
  }
  unaryExpression(ctx: UnaryExpressionCtx, param?: any) {
    this.mustVisit(ctx.primary);
  }
  primary(ctx: PrimaryCtx, param?: any) {
    // This is a list...
    this.mustVisit(ctx.primaryPrefix);
    this.maybeVisit(ctx.primarySuffix);
  }
  primaryPrefix(ctx: PrimaryPrefixCtx, param?: any) {
    this.maybeVisit(ctx.newExpression);
    this.maybeVisit(ctx.fqnOrRefType);
    this.maybeVisit(ctx.literal);
  }
  primarySuffix(ctx: PrimarySuffixCtx, param?: any) {
    // console.log('primarySuffix', ctx);
    codeAdd(getContent(ctx));
  }
  fqnOrRefType(ctx: FqnOrRefTypeCtx, param?: any) {
    this.mustVisit(ctx.fqnOrRefTypePartFirst);
    codeAdd(getContent(ctx.fqnOrRefTypePartRest));
  }
  fqnOrRefTypePartFirst(ctx: FqnOrRefTypePartFirstCtx, param?: any) {
    this.mustVisit(ctx.fqnOrRefTypePartCommon);
  }
  fqnOrRefTypePartCommon(ctx: FqnOrRefTypePartCommonCtx, param?: any) {
    const lambdaArg = tokenStack.peek();
    if (lambdaArg && lambdaArg.kind === TokenKind.LambdaParam) {
      codeAdd('func');
    } else {
      codeAdd(getContent(ctx));
    }
  }
  newExpression(ctx: NewExpressionCtx, param?: any) {
    // console.log('new', ctx);
    codeAdd('new');
    this.maybeVisit(ctx.unqualifiedClassInstanceCreationExpression);
  }
  unqualifiedClassInstanceCreationExpression(
    ctx: UnqualifiedClassInstanceCreationExpressionCtx,
    param?: any,
  ) {
    const typeName = getContent(ctx.classOrInterfaceTypeToInstantiate);
    codeAdd(' ', typeMap.get(typeName) || typeName);
    tokenStack.push(MakeToken(TokenKind.NewType, typeName));
    this.maybeVisit(ctx.argumentList);
    tokenStack.pop();
  }
  argumentList(ctx: ArgumentListCtx, param?: any) {
    codeAdd('(');
    const top = tokenStack.peek();
    ctx.expression.forEach((node, idx) => {
      const prefix = idx === 0 ? '' : ', ';
      const code = getContent(node);
      if (
        idx === 2 &&
        top.kind === TokenKind.NewType &&
        top.value === 'ConfigurablePoseD'
      ) {
        codeAdd(prefix, 'toRadians(', code, ')');
      } else {
        codeAdd(prefix, code);
      }
    });
    codeSpit(');');
  }
}

async function main(): Promise<void> {
  // Make the output directory structure
  try {
    await fs.mkdir(outputLocation, { recursive: true });
  } catch (e) {
    console.error(e);
  }

  const transformer = new AutoConstVisitor();
  // Remove the comments then parse the file
  for (const file of files) {
    const origFileContents = await fs.readFile(file, 'utf8');
    const contents = removeComments(origFileContents);
    curFile = contents.join('\n');
    const cstNode = parse(curFile);
    transformer.visit(cstNode);
  }

  /*
  parsedFiles.forEach((cst, key) => {
    console.log('Name: ', key);
    if (hasField(cst.children, 'ordinaryCompilationUnit')) {
      console.log(typeof cst.children.ordinaryCompilationUnit);
      console.log(cst.children.ordinaryCompilationUnit);
    }
  });
  */
  // console.log(parsedFiles);

  // TODO: Extract the drivebase specs/RR setup code from the bot, maybe?

  let output = `package ${packageDir.join('.')};
/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!!!!!!!!!WARNING!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!! DO NOT EDIT THIS FILE !!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!!!!!!!!!WARNING!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * Any edits you make will get obliterated when you build.
 * Instead, make edits to ${files.length > 1 ? 'these files' : 'this file'}:
 *
 * ${files.join('\n * ')}
 *
 * and your changes will be reflected in here when you next build.
 *
 * If you're struggling to get things to compile properly, know that you should
 * not be using "fully qualified" typenames, but should import the whole
 * types, and the build will fix them up to work for MeepMeep.
 *
 * If you're not using fully qualified names, and things are still messed up
 * you should reach out to Kevin to figure out what's going on.
 */
`;

  /* You can move this before the ` to see the raw input to the script:
   * ${process.argv.join("\n * ")};
   */

  output += collectImports();

  output += `\n\npublic class ${className} {\n`;
  output += theCode.join('\n');
  output += '\n}\n';
  await fs.writeFile(path.join(outputLocation, `${className}.java`), output);
}

// Produces a single, unique set of import statements from the group of files.
function collectImports(): string {
  return [...imports]
    .filter((val) => !removeImports.has(val))
    .sort()
    .map((i) => `import ${i};`)
    .join('\n');
}

main().catch(console.error);
