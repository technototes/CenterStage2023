import * as fs from 'fs/promises';
import * as path from 'path';
import { BaseJavaCstVisitorWithDefaults, parse, } from 'java-parser';
import { chkBothOf, chkFieldType, hasField, hasFieldType, hasStrField, isArray, isNonNullable, isNumber, } from '@freik/typechk';
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
    ]
]);
const extraImports = ['static java.lang.Math.toRadians'];
const typeMap = new Map([
    ['ConfigurablePose', 'Pose2d'],
    ['ConfigurablePoseD', 'Pose2d'],
    ['Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>', 'Supplier<Trajectory>']
]);
/*** END CONFIGURATION STUFF ***/
console.log(process.argv);
const [, , outDir, filesAsString] = process.argv;
const outputLocation = path.join(outDir, 'generated-sources', ...packageDir);
// We're only finding files that include "auto" and "const" in their paths.
const filesNoBrackets = filesAsString.substring(1, filesAsString.length - 1);
const files = filesNoBrackets
    .split(', ')
    .filter((val) => val.toLocaleLowerCase().indexOf('auto') >= 0 &&
    val.toLocaleLowerCase().indexOf('const') >= 0);
// Filename to string[] where the contents have had comments stripped
// and blank lines are removed.
const fileContents = new Map();
const parsedFiles = new Map();
// A crappy, poorly written context stack
var TokenKind;
(function (TokenKind) {
    TokenKind[TokenKind["NewType"] = 0] = "NewType";
    TokenKind[TokenKind["LambdaParam"] = 1] = "LambdaParam";
    TokenKind[TokenKind["DeclType"] = 2] = "DeclType";
})(TokenKind || (TokenKind = {}));
;
const tokenStack = [];
function pushThing(kind, value) {
    tokenStack.push({ kind, value });
}
function popThing() {
    tokenStack.pop();
}
function topThing() {
    return tokenStack[tokenStack.length - 1];
}
function topOfKind(tk) {
    for (let i = tokenStack.length - 1; i >= 0; i--) {
        if (tokenStack[i].kind === tk) {
            return tokenStack[i].value;
        }
    }
    return undefined;
}
// A really dumb symbol table
const symbolTypes = new Map();
// Some output state:
const imports = new Set([removeImports.values()].map(v => getImportKey('', v, '')));
let prev = '';
function codeSpit(...args) {
    if (prev.length > 0) {
        args = [prev, ...args];
        prev = '';
    }
    if (args.length > 1) {
        console.log(">>>", args.join(""));
    }
    else {
        console.log(">>>", args[0]);
    }
}
function codeAdd(...args) {
    prev += args.join("");
}
function getImportKey(stat, imprt, star) {
    return `${stat} ${imprt}.${star}`;
}
function unsupported(name, obj) {
    if (obj.hasOwnProperty(name)) {
        throw new Error(`${name} is unsupported`);
    }
}
function required(obj, message) {
    if (!obj) {
        throw new Error(message ?? 'Required type not found :(');
    }
    return true;
}
function assert(obj, message) {
    if (!obj) {
        throw new Error(message);
    }
    return true;
}
let curFile = '';
function getCode(start, end) {
    return curFile.substring(start, end + 1);
}
function getItemContent(f) {
    if (hasFieldType(f, 'location', chkBothOf(chkFieldType('startOffset', isNumber), chkFieldType('endOffset', isNumber)))) {
        return getCode(f.location.startOffset, f.location.endOffset);
    }
    return '';
}
function getContent(field, sep) {
    return isArray(field) ? field.map(getItemContent).join(sep ?? " ") : getItemContent(field);
}
let trimStart = -1;
let trimEnd = -1;
function trimCode(start, end, forget) {
    end++;
    // Do we include a new line?
    const prevEnd = end;
    const nl = curFile.indexOf('\n', start);
    if (nl < end) {
        end = nl;
    }
    if (end - start > 80) {
        // Just cut it down to 50 characters
        end = start + 50;
    }
    if (!forget) {
        if (trimStart === start && trimEnd === end) {
            return " ^";
        }
        trimStart = start;
        trimEnd = end;
    }
    return end === prevEnd
        ? curFile.substring(start, end)
        : `${curFile.substring(start, end)}...`;
}
class AutoConstVisitor extends BaseJavaCstVisitorWithDefaults {
    constructor() {
        super();
        this.output = [];
        this.depth = '';
        this.validateVisitor();
    }
    maybeVisit(field) {
        if (!isNonNullable(field))
            return;
        let content = '';
        if (isArray(field)) {
            const multiple = field.length > 1;
            for (let i = 0; i < field.length; i++) {
                const f = field[i];
                if (hasStrField(f, 'name')) {
                    content += i !== 0 ? ', ' : f.name;
                }
                content += multiple ? `[${i}]:` : '=>';
                if (hasFieldType(f, 'location', chkBothOf(chkFieldType('startOffset', isNumber), chkFieldType('endOffset', isNumber)))) {
                    content += trimCode(f.location.startOffset, f.location.endOffset);
                }
            }
        }
        if (content /* && !content.endsWith("^")*/)
            console.log(`${this.depth}// ${content}`);
        const prevDepth = this.depth;
        this.depth += " ";
        this.visit(field);
        this.depth = prevDepth;
    }
    mustVisit(obj) {
        if (isNonNullable(obj)) {
            this.maybeVisit(obj);
        }
        else {
            throw new Error(`Missing required child element`);
        }
    }
    // Rewire the package:
    packageDeclaration(ctx, param) {
        codeSpit("// Original package: ", ctx.Identifier.map(token => token.image).join('.'));
        codeSpit("package ", packageDir.join('.'), ";");
    }
    // Copy, reroute, or remove imports:
    importDeclaration(ctx, param) {
        // console.log("import: ", ctx);
        const stat = ctx.Static ? 'static ' : '';
        const star = ctx.Star ? '*' : '';
        const imprt = ctx.packageOrTypeName.map(cst => cst.children.Identifier.map(tok => tok.image).join('.')).join('.');
        const actual = importMap.has(imprt) ? importMap.get(imprt) : imprt;
        const key = `${stat} ${actual}.${star}`;
        if (!imports.has(key)) {
            imports.add(key);
            codeSpit("import ", stat, actual, star, ';');
        }
    }
    // Filter out any '@Config's from class declarations
    classDeclaration(ctx, param) {
        // console.log("classDecl: ", ctx)
        if (ctx.classModifier) {
            const modifers = ctx.classModifier.map(mod => getItemContent(mod)).filter(v => v != '@Config').join(' ') + " ";
            codeAdd(modifers);
        }
        this.mustVisit(ctx.normalClassDeclaration);
    }
    normalClassDeclaration(ctx, param) {
        codeAdd("class ");
        this.mustVisit(ctx.typeIdentifier);
        codeSpit(" {");
        this.mustVisit(ctx.classBody);
        codeSpit("}");
    }
    // spit out the type identifier
    typeIdentifier(ctx, param) {
        codeAdd(ctx.Identifier.map(tok => tok.image).join('.'));
    }
    // We're picky about the type of class bodies supported
    classBodyDeclaration(ctx, param) {
        // No constructor!
        unsupported('constructorDeclaration', ctx);
        // No instance initializers!
        unsupported('instanceInitializer', ctx);
        // No static initializers!
        unsupported('staticInitializer', ctx);
        this.mustVisit(ctx.classMemberDeclaration);
    }
    classMemberDeclaration(ctx, param) {
        // We don't support methods!
        unsupported('methodDeclaration', ctx);
        // We don't support interfaces, either.
        unsupported('interfaceDeclaration', ctx);
        this.maybeVisit(ctx.fieldDeclaration);
        this.maybeVisit(ctx.classDeclaration);
    }
    fieldDeclaration(ctx, param) {
        // Add the field modifiers
        codeAdd(getContent(ctx.fieldModifier), " ");
        this.mustVisit(ctx.unannType);
        this.mustVisit(ctx.variableDeclaratorList);
        if (topThing()?.kind === TokenKind.DeclType) {
            popThing();
        }
        else {
            throw new Error("Unexpected stack situation");
        }
    }
    unannType(ctx, param) {
        this.maybeVisit(ctx.unannReferenceType);
        if (ctx.unannPrimitiveTypeWithOptionalDimsSuffix) {
            codeAdd(getContent(ctx.unannPrimitiveTypeWithOptionalDimsSuffix));
        }
    }
    unannReferenceType(ctx, param) {
        this.mustVisit(ctx.unannClassOrInterfaceType);
    }
    unannClassOrInterfaceType(ctx, param) {
        const typeName = getContent(ctx.unannClassType);
        pushThing(TokenKind.DeclType, typeName);
        codeAdd(typeMap.get(typeName) || typeName);
    }
    variableDeclaratorList(ctx, param) {
        this.mustVisit(ctx.variableDeclarator);
    }
    variableDeclarator(ctx, param) {
        this.mustVisit(ctx.variableDeclaratorId);
        this.mustVisit(ctx.variableInitializer);
    }
    variableDeclaratorId(ctx, param) {
        if (required(ctx.Identifier, 'Unsupported Identifier-less varDeclID')) {
            const varName = ctx.Identifier[0].image;
            codeAdd(" ", varName, " = ");
            // This is setting us up to keep track of variable name types, 
            // so we can yoink ".toPose()" modifiers later in the file...
            const declType = topThing();
            if (declType?.kind === TokenKind.DeclType) {
                symbolTypes.set(varName, declType.value);
            }
        }
    }
    variableInitializer(ctx, param) {
        this.mustVisit(ctx.expression);
    }
    expression(ctx, param) {
        this.maybeVisit(ctx.lambdaExpression);
        this.maybeVisit(ctx.ternaryExpression);
    }
    lambdaExpression(ctx, param) {
        const params = getContent(ctx.lambdaParameters, ", ");
        // We need to transform b -> b.apply(...) to () -> func.apply(...)
        // Push the parameter(s) to the lambda for the lambdaBody to process
        pushThing(TokenKind.LambdaParam, params);
        this.mustVisit(ctx.lambdaBody);
    }
    lambdaBody(ctx, param) {
        const expr = getContent(ctx.expression);
        console.log("Expr:  ", expr);
        unsupported("block", ctx);
        const top = topThing();
        if (top && top.kind === TokenKind.LambdaParam && expr.startsWith(`${top.value}.apply(`)) {
            const cleanupExpr = expr.substring(top.value.length).replaceAll(".toPose()", "");
            codeSpit('() -> ', 'func', cleanupExpr);
        }
        else if (top) {
            codeSpit(top.value, ' -> ', expr);
        }
        else {
            throw new Error("Unexpected Lambda body stack");
        }
        popThing();
    }
    // Ternary expression is the container for all non-lambdas
    // which is definitely a little weird, but whatever...
    ternaryExpression(ctx, param) {
        unsupported('QuestionMark', ctx);
        unsupported('Colon', ctx);
        this.mustVisit(ctx.binaryExpression);
    }
    binaryExpression(ctx, param) {
        assert(!(ctx.AssignmentOperator ||
            ctx.BinaryOperator ||
            ctx.Greater ||
            ctx.Instanceof ||
            ctx.Less ||
            ctx.pattern ||
            ctx.referenceType), 'unsupported child of binary expression');
        this.maybeVisit(ctx.expression);
        this.mustVisit(ctx.unaryExpression);
    }
    unaryExpression(ctx, param) {
        this.mustVisit(ctx.primary);
    }
    primary(ctx, param) {
        // This is a list...
        this.maybeVisit(ctx.primarySuffix);
        this.mustVisit(ctx.primaryPrefix);
    }
    primaryPrefix(ctx, param) {
        this.maybeVisit(ctx.newExpression);
        this.maybeVisit(ctx.fqnOrRefType);
        this.maybeVisit(ctx.literal);
    }
    primarySuffix(ctx, param) {
        console.log('primarySuffix', ctx);
    }
    fqnOrRefType(ctx, param) {
        this.mustVisit(ctx.fqnOrRefTypePartFirst);
        codeAdd(getContent(ctx.fqnOrRefTypePartRest));
    }
    fqnOrRefTypePartFirst(ctx, param) {
        this.mustVisit(ctx.fqnOrRefTypePartCommon);
    }
    fqnOrRefTypePartCommon(ctx, param) {
        const lambdaArg = topThing();
        if (lambdaArg && lambdaArg.kind === TokenKind.LambdaParam) {
            codeAdd('func');
        }
        else {
            codeAdd(getContent(ctx));
        }
    }
    newExpression(ctx, param) {
        // console.log('new', ctx);
        this.maybeVisit(ctx.unqualifiedClassInstanceCreationExpression);
    }
    unqualifiedClassInstanceCreationExpression(ctx, param) {
        const typeName = getContent(ctx.classOrInterfaceTypeToInstantiate);
        codeAdd(" ", typeMap.get(typeName) || typeName);
        pushThing(TokenKind.NewType, typeName);
        this.maybeVisit(ctx.argumentList);
        popThing();
    }
    argumentList(ctx, param) {
        codeAdd("(");
        const top = topThing();
        ctx.expression.forEach((node, idx) => {
            const prefix = idx === 0 ? '' : ", ";
            const code = getContent(node);
            if (idx === 2 && top.kind === TokenKind.NewType && top.value === "ConfigurablePoseD") {
                codeAdd(prefix, "toRadians(", code, ")");
            }
            else {
                codeAdd(prefix, code);
            }
        });
        codeSpit(");");
    }
}
async function main() {
    // Make the output directory structure
    try {
        await fs.mkdir(outputLocation, { recursive: true });
    }
    catch (e) {
        console.error(e);
    }
    const transformer = new AutoConstVisitor();
    // First, Remove the comments and collect the result in the fileContents map.
    for (const file of files) {
        const contents = await removeComments(file);
        curFile = contents.join('\n');
        const cstNode = parse(curFile);
        parsedFiles.set(file, cstNode);
        transformer.visit(cstNode);
        fileContents.set(file, contents.filter((c) => c.trim().length > 0));
    }
    parsedFiles.forEach((cst, key) => {
        console.log('Name: ', key);
        if (hasField(cst.children, 'ordinaryCompilationUnit')) {
            console.log(typeof cst.children.ordinaryCompilationUnit);
            console.log(cst.children.ordinaryCompilationUnit);
        }
    });
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
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!! DO NOT EDIT THIS FILE !!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!!!!!!!!!!WARNING!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * Any edits you make will get obliterated when you build.
 * Instead, make edits to these files:
 *
 * ${files.join('\n * ')}
 *
 * and your changes will be reflected in here when you next build.
 *
 * If you're struggling to get things to compile properly, know that you should
 * not be using "fully qualified" typenames, but should only import the whole
 * types, and the build will fix them up to work for MeepMeep.
 * 
 * If you're note using fully qualified names, and things are still messed up
 * you should reach out to Kevin to figure out what's going on.
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
    await fs.writeFile(path.join(outputLocation, 'MeepMeepConstants.java'), output);
}
const isPackage = /^\s*package\s/;
const isImport = /^\s*import\s/;
const noStaticClass = /^(\s*)public\s+static\s+class\s+/;
// Rip off all the imports and transform the file as necessary,
// returning the output as a string.
function processFile(lines) {
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
function fixConfigurable(line) {
    let res = '';
    let prevStart = 0;
    for (let cfgIndex = line.indexOf(CONFIG); cfgIndex >= 0; cfgIndex = line.indexOf(CONFIG, cfgIndex + 1)) {
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
async function removeComments(file) {
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
                }
                else if (char === "'") {
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
function cleanupImport(line) {
    return line.replace(/import\s+/g, '').replace(/;.*/g, '');
}
// Produces a single, unique set of import statements from the group of files.
function collectImports() {
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
//# sourceMappingURL=preprocessor.js.map