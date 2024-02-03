import * as fs from 'fs/promises';
import * as path from 'path';
import { BaseJavaCstVisitorWithDefaults, BinaryExpressionCtx, ClassBodyCtx, ClassBodyDeclarationCtx, ClassDeclarationCtx, ClassMemberDeclarationCtx, CstNode, ExpressionCtx, ExpressionStatementCtx, FieldDeclarationCtx, ImportDeclarationCtx, LambdaExpressionCtx, LambdaParameterCtx, LambdaParametersCtx, PackageDeclarationCtx, PrimaryCtx, PrimaryPrefixCtx, PrimarySuffixCtx, TernaryExpressionCtx, UnaryExpressionCtx, VariableDeclaratorCtx, VariableDeclaratorIdCtx, VariableDeclaratorListCtx, VariableInitializerCtx, parse } from "java-parser";
import { hasField, hasFieldType, isArray, isNonNullable } from '@freik/typechk';

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
]);
const extraImports = ['static java.lang.Math.toRadians'];
/*** END CONFIGURATION STUFF ***/

console.log(process.argv);
const [, , outDir, filesAsString] = process.argv;
const outputLocation = path.join(outDir, 'generated-sources', ...packageDir);

// We're only finding files that include "auto" and "const" in their paths.
const filesNoBrackets = filesAsString.substring(1, filesAsString.length - 1);
const files = filesNoBrackets
  .split(', ')
  .filter(
    (val) =>
      val.toLocaleLowerCase().indexOf('auto') >= 0 &&
      val.toLocaleLowerCase().indexOf('const') >= 0,
  );

// Filename to string[] where the contents have had comments stripped
// and blank lines are removed.
const fileContents = new Map<string, string[]>();
const parsedFiles = new Map<string, CstNode>();

function unsupported(name: string, obj: object): void {
  if (obj.hasOwnProperty(name)) {
    throw new Error(`${name} is unsupported`);
  }
}
function required(obj: unknown, message?: string): obj is NonNullable<unknown> {
  if (!obj) {
    throw new Error(message ?? "Required type not found :(");
  }
  return true;
}
function assert(obj: unknown, message: string): obj is NonNullable<unknown> {
  if (!obj) {
    throw new Error(message);
  }
  return true;
}
class AutoConstVisitor extends BaseJavaCstVisitorWithDefaults {
  output: string[];
  constructor() {
    super();
    this.output = [];
    this.validateVisitor();
  }
  packageDeclaration(ctx: PackageDeclarationCtx, param?: unknown) {
    // console.log("package:", ctx);
  }
  importDeclaration(ctx: ImportDeclarationCtx, param?: unknown) {
    // console.log("import: ", ctx);
  }
  classDeclaration(ctx: ClassDeclarationCtx, param?: any) {
    // console.log("classDecl: ", ctx)
    if (ctx.normalClassDeclaration) {
      this.visit(ctx.normalClassDeclaration);
    }
  }
  classBodyDeclaration(ctx: ClassBodyDeclarationCtx, param?: any) {
    // console.log("classBody: ", ctx);
    if (ctx.classMemberDeclaration) {
      this.visit(ctx.classMemberDeclaration);
    }
    unsupported("constructorDeclaration", ctx);
    unsupported("instanceInitializer", ctx);
    unsupported("staticInitializer", ctx);
  }
  classMemberDeclaration(ctx: ClassMemberDeclarationCtx, param?: any) {
    unsupported("methodDeclaration", ctx);
    unsupported("interfaceDeclaration", ctx);
    if (ctx.fieldDeclaration) {
      this.visit(ctx.fieldDeclaration);
    }
    if (ctx.classDeclaration) {
      this.visit(ctx.classDeclaration);
    }
  }
  fieldDeclaration(ctx: FieldDeclarationCtx, param?: any) {
    // console.log("field: ", ctx);
    // TODO: Validate field modifiers "public static"
    this.visit(ctx.variableDeclaratorList);
  }
  variableDeclaratorList(ctx: VariableDeclaratorListCtx, param?: any) {
    // console.log("varDeclList: ", ctx);
    this.visit(ctx.variableDeclarator);
  }
  variableDeclarator(ctx: VariableDeclaratorCtx, param?: any) {
    // console.log("varDecl: ", ctx);
    this.visit(ctx.variableDeclaratorId);
    if (required(ctx.variableInitializer, "Uninitialized variable not supported")) {
      this.visit(ctx.variableInitializer);
    }
  }
  variableDeclaratorId(ctx: VariableDeclaratorIdCtx, param?: any) {
    if (required(ctx.Identifier, 'Unsupported Identifier-less varDeclID')) {
      console.log('varDeclId: ', ctx.Identifier[0].image);
    }
  }
  variableInitializer(ctx: VariableInitializerCtx, param?: any) {
    console.log("varInit: ", ctx);
    if (required(ctx.expression, "Unsupported varInit type")) {
      this.visit(ctx.expression);
    }
  }
  expression(ctx: ExpressionCtx, param?: any) {
    if (ctx.lambdaExpression) {
      this.visit(ctx.lambdaExpression);
    }
    if (ctx.ternaryExpression) {
      this.visit(ctx.ternaryExpression);
    }
  }
  lambdaExpression(ctx: LambdaExpressionCtx, param?: any) {
    this.visit(ctx.lambdaParameters);
    this.visit(ctx.lambdaBody);
  }
  // Ternary expression is the container for all non-lambdas
  // which is definitely a little weird, but whatever...
  ternaryExpression(ctx: TernaryExpressionCtx, param?: any) {
    unsupported("QuestionMark", ctx);
    unsupported("Colon", ctx);
    this.visit(ctx.binaryExpression);
  }
  binaryExpression(ctx: BinaryExpressionCtx, param?: any) {
    assert(!(ctx.AssignmentOperator || ctx.BinaryOperator || ctx.Greater || ctx.Instanceof ||
      ctx.Less || ctx.pattern || ctx.referenceType), "unsupported child of binary expression");
    if (ctx.expression) {
      this.visit(ctx.expression);
    }
    this.visit(ctx.unaryExpression);
  }
  unaryExpression(ctx: UnaryExpressionCtx, param?: any) {
    this.visit(ctx.primary);
  }
  primary(ctx: PrimaryCtx, param?: any) {
    if (ctx.primarySuffix) {
      this.visit(ctx.primarySuffix);
    }
    this.visit(ctx.primaryPrefix);
  }
  primaryPrefix(ctx: PrimaryPrefixCtx, param?: any) {
    console.log("primaryPrefix", ctx);
  }
  primarySuffix(ctx: PrimarySuffixCtx, param?: any) {
    console.log("primarySuffix", ctx);
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
  // First, Remove the comments and collect the result in the fileContents map.
  for (const file of files) {
    const contents = await removeComments(file);
    const cstNode = parse(contents.join('\n'));
    parsedFiles.set(file, cstNode);
    transformer.visit(cstNode);
    fileContents.set(
      file,
      contents.filter((c) => c.trim().length > 0),
    );
  }

  parsedFiles.forEach((cst, key) => {
    console.log("Name: ", key);
    if (hasField(cst.children, 'ordinaryCompilationUnit')) {
      console.log(typeof cst.children.ordinaryCompilationUnit);
      console.log(cst.children.ordinaryCompilationUnit);
    }
  })
  // console.log(parsedFiles);

  // TODO: Finish the simple stuff up.

  // At this point, I have a 'files' array of only things that
  // include "auto" and "const". These files need to have their package
  // lines removed and their import types adjusted to be MeepMeep compatible.
  // Then each member variable needs to be transformed from a TechnoLib type
  // to a MeepMeep-compatible type. This is sort of complicated. It might
  // make more sense to just walk back from TechnoLib types to the raw
  // RoadRunner types instead. We'll have to see how bad it gets...

  // Once that's done, I could move on to extracting the robot information
  // from Technolib/RR setup code from the bot, but that's just not very
  // difficult, and doesn't change regularly, so it's probably not worth
  // the effort.

  let output = `/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!!!!!!!!!WARNING!!!!!!!!!!!!!
 * !!!! DO NOT EDIT THIS FILE !!!!
 * !!!!!!!!!!!WARNING!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * Any edits you make will get obliterated when you build. Instead, make edits
 * to these files:
 *
 * ${files.join('\n * ')}
 *
 * and those changes will be reflected in here when you next build.
 *
 * If you're struggling to get things to compile properly, know that you should
 * not be using "fully qualified" typenames, but should only import the whole
 * types, and the build will fix them up to work for MeepMeep.
 */

package ${packageDir.join('.')};

`;
  output += collectImports();
  output += '\n\npublic class MeepMeepConstants {\n';

  for (const lines of fileContents.values()) {
    console.log('Lines: ', lines.length);
    output += processFile(lines);
  }
  output += '\n}\n';

  await fs.writeFile(
    path.join(outputLocation, 'MeepMeepConstants.java'),
    output,
  );
}

const isPackage = /^\s*package\s/;
const isImport = /^\s*import\s/;
const noStaticClass = /^(\s*)public\s+static\s+class\s+/;
// Rip off all the imports and transform the file as necessary,
// returning the output as a string.
function processFile(lines: string[]): string {
  // Skip package, imports
  let result = '';
  let inHeader = true;
  for (let line of lines) {
    if (inHeader && (isPackage.test(line) || isImport.test(line))) {
      continue;
    }
    inHeader = false;
    // No FTC Dashboard
    if (line.trim() === '@Config') {
      continue;
    }
    // Deal with old Java syntax
    line = line.replace(noStaticClass, '$1public class ');
    // TODO: More cleanup here
    if (line.indexOf('Configurable') >= 0) {
      // First one: ConfigurableD(a, b, c) => Pose2d(a, b, toRadians(c))
      line = fixConfigurable(line);
    }
    // Followed immediately by the removal of .toPose()'s
    result += line + '\n';
  }
  return result;
}


// This is a helper to convert "ConfigurablePose[D]" into Pose2d's
const CONFIG = 'ConfigurablePose';
const NUMBER = /^\s*(-?\s*(?:(\d*\.?\d+)|(\d+\.?\d*)))\s*$/;
function fixConfigurable(line: string): string {
  let res = '';
  let prevStart = 0;
  for (
    let cfgIndex = line.indexOf(CONFIG);
    cfgIndex >= 0;
    cfgIndex = line.indexOf(CONFIG, cfgIndex + 1)
  ) {
    // Find the end of the word
    let pos = cfgIndex + CONFIG.length;
    const isCfgD = line[pos] === 'D';
    res += line.substring(prevStart, cfgIndex) + 'Pose2d';
    pos += isCfgD ? 1 : 0;
    prevStart = pos;
    /*
    if (isCfgD && line[pos] === '(') {
      const end = findClosingParen(line, pos);
      const comma = findPreviousCommand(line, pos, end);
      const val = NUMBER.exec(line.substring(command, end));

    }
    */
  }
  res += line.substring(prevStart);
  return res;
}

const PLAIN = Symbol('plain');
const IN_DQSTRING = Symbol('in dqstring');
const IN_SQSTRING = Symbol('in sqstring');
const IN_MLCOMMENT = Symbol('in multiline comment');
const IN_SLCOMMENT = Symbol('in singleline comment');

async function removeComments(file: string): Promise<string[]> {
  const contents = await fs.readFile(file, 'utf8');
  // This is a big ol' state-machine-based parser to remove
  // comments. I should draw the state machine out explicitly
  // sometime. Currently, I'm assuming it's not perfect, but
  // it works well enough for now...
  let result = '';
  let state = PLAIN;
  let justSawSlash = false;
  let justSawBackslash = false;
  let justSawStar = false;
  for (const char of contents) {
    switch (state) {
      case IN_SLCOMMENT:
        if (char === '\n') {
          state = PLAIN;
          result += '\n';
        }
        continue;
      case IN_MLCOMMENT:
        if (justSawStar && char === '/') {
          state = PLAIN;
          justSawStar = false;
          continue;
        }
        justSawStar = char === '*';
        continue;
      case IN_DQSTRING:
        if (char === '"') {
          state = justSawBackslash ? IN_DQSTRING : PLAIN;
        }
        break;
      case IN_SQSTRING:
        if (char === '"') {
          state = justSawBackslash ? IN_SQSTRING : PLAIN;
        }
        break;
      case PLAIN:
        if (justSawSlash) {
          if (char === '/') {
            justSawSlash = false;
            state = IN_SLCOMMENT;
            continue;
          }
          if (char === '*') {
            justSawSlash = false;
            state = IN_MLCOMMENT;
            continue;
          }
          // Output the slash that we saw in the previous iteration,
          // since it wasn't part of a comment.
          result += '/';
        }
        if (char === '"') {
          state = IN_DQSTRING;
        } else if (char === "'") {
          state = IN_SQSTRING;
        }
        break;
    }
    justSawSlash = char === '/';
    justSawBackslash = char === '\\';
    if (!justSawSlash && char !== '\r') {
      // For a front-slash not in a string, don't output it.
      // It might be the beginning of a comment. We handle it above...
      result += char;
    }
  }
  // Deal with any trailing states
  if (justSawSlash && state === PLAIN) {
    result += '/';
  }
  return result.split('\n');
}

// Just get the import path and nothing else.
function cleanupImport(line: string): string {
  return line.replace(/import\s+/g, '').replace(/;.*/g, '');
}

// Produces a single, unique set of import statements from the group of files.
function collectImports(): string {
  const imports = new Set(extraImports);
  for (const [, lines] of fileContents) {
    for (let line of lines) {
      const trimmed = line.trim();
      if (trimmed.startsWith('import')) {
        const theImport = cleanupImport(trimmed);
        // Don't add the import if we're supposed to remove it.
        if (!removeImports.has(theImport)) {
          // Get a 'mapped' import and add it's replacement if it exists.
          const mapped = importMap.get(theImport);
          imports.add(mapped || theImport);
        }
      }
    }
  }
  return Array.from(imports)
    .sort()
    .map((i) => `import ${i};`)
    .join('\n');
}

main().catch(console.error);
