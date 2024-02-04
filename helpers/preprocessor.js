import * as fs from 'fs/promises';
import * as path from 'path';
import { BaseJavaCstVisitorWithDefaults, parse, } from 'java-parser';
import { hasField, hasFieldType, isFunction, isNonNullable } from '@freik/typechk';
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
    .filter((val) => val.toLocaleLowerCase().indexOf('auto') >= 0 &&
    val.toLocaleLowerCase().indexOf('const') >= 0);
// Filename to string[] where the contents have had comments stripped
// and blank lines are removed.
const fileContents = new Map();
const parsedFiles = new Map();
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
function visit(desc, obj) {
    if (isNonNullable(obj) && hasField(obj, desc) && hasFieldType(obj, "visit", isFunction)) {
        console.log(`// { // ${desc}`);
        obj.visit.apply(obj, obj[desc]);
        console.log(`// }  ${desc}`);
    }
}
class AutoConstVisitor extends BaseJavaCstVisitorWithDefaults {
    constructor() {
        super();
        this.output = [];
        this.validateVisitor();
    }
    packageDeclaration(ctx, param) {
        // console.log("package:", ctx);
        // TODO: Switch this to our new package
    }
    importDeclaration(ctx, param) {
        // console.log("import: ", ctx);
        // TODO: Reroute imports as needed
    }
    classDeclaration(ctx, param) {
        // console.log("classDecl: ", ctx)
        visit("normalClassDeclaration", this);
    }
    classBodyDeclaration(ctx, param) {
        if (ctx.classMemberDeclaration) {
            console.log("// class body {");
            this.visit(ctx.classMemberDeclaration);
            console.log("// } class body");
        }
        unsupported('constructorDeclaration', ctx);
        unsupported('instanceInitializer', ctx);
        unsupported('staticInitializer', ctx);
    }
    classMemberDeclaration(ctx, param) {
        unsupported('methodDeclaration', ctx);
        unsupported('interfaceDeclaration', ctx);
        if (ctx.fieldDeclaration) {
            this.visit(ctx.fieldDeclaration);
        }
        if (ctx.classDeclaration) {
            this.visit(ctx.classDeclaration);
        }
    }
    fieldDeclaration(ctx, param) {
        // console.log("field: ", ctx);
        // TODO: Validate field modifiers "public static"
        this.visit(ctx.variableDeclaratorList);
    }
    variableDeclaratorList(ctx, param) {
        // console.log("varDeclList: ", ctx);
        this.visit(ctx.variableDeclarator);
    }
    variableDeclarator(ctx, param) {
        // console.log("varDecl: ", ctx);
        this.visit(ctx.variableDeclaratorId);
        if (required(ctx.variableInitializer, 'Uninitialized variable not supported')) {
            this.visit(ctx.variableInitializer);
        }
    }
    variableDeclaratorId(ctx, param) {
        if (required(ctx.Identifier, 'Unsupported Identifier-less varDeclID')) {
            console.log('varDeclId: ', ctx.Identifier[0].image);
        }
    }
    variableInitializer(ctx, param) {
        console.log('varInit: ', ctx);
        if (required(ctx.expression, 'Unsupported varInit type')) {
            this.visit(ctx.expression);
        }
    }
    expression(ctx, param) {
        if (ctx.lambdaExpression) {
            this.visit(ctx.lambdaExpression);
        }
        if (ctx.ternaryExpression) {
            this.visit(ctx.ternaryExpression);
        }
    }
    lambdaExpression(ctx, param) {
        this.visit(ctx.lambdaParameters);
        this.visit(ctx.lambdaBody);
    }
    // Ternary expression is the container for all non-lambdas
    // which is definitely a little weird, but whatever...
    ternaryExpression(ctx, param) {
        unsupported('QuestionMark', ctx);
        unsupported('Colon', ctx);
        this.visit(ctx.binaryExpression);
    }
    binaryExpression(ctx, param) {
        assert(!(ctx.AssignmentOperator ||
            ctx.BinaryOperator ||
            ctx.Greater ||
            ctx.Instanceof ||
            ctx.Less ||
            ctx.pattern ||
            ctx.referenceType), 'unsupported child of binary expression');
        if (ctx.expression) {
            this.visit(ctx.expression);
        }
        this.visit(ctx.unaryExpression);
    }
    unaryExpression(ctx, param) {
        this.visit(ctx.primary);
    }
    primary(ctx, param) {
        if (ctx.primarySuffix) {
            this.visit(ctx.primarySuffix);
        }
        this.visit(ctx.primaryPrefix);
    }
    primaryPrefix(ctx, param) {
        console.log('primaryPrefix', ctx);
        if (ctx.newExpression) {
            this.visit(ctx.newExpression);
        }
        if (ctx.fqnOrRefType) {
            this.visit(ctx.fqnOrRefType);
        }
    }
    primarySuffix(ctx, param) {
        console.log('primarySuffix', ctx);
    }
    fqnOrRefType(ctx, param) {
        console.log('Fqn', ctx);
    }
    newExpression(ctx, param) {
        console.log('new', ctx);
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
        const cstNode = parse(contents.join('\n'));
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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicHJlcHJvY2Vzc29yLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vaGVscGVycy1zcmMvcHJlcHJvY2Vzc29yLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sS0FBSyxFQUFFLE1BQU0sYUFBYSxDQUFDO0FBQ2xDLE9BQU8sS0FBSyxJQUFJLE1BQU0sTUFBTSxDQUFDO0FBQzdCLE9BQU8sRUFDTCw4QkFBOEIsRUEwQjlCLEtBQUssR0FDTixNQUFNLGFBQWEsQ0FBQztBQUNyQixPQUFPLEVBQUUsUUFBUSxFQUFFLFlBQVksRUFBVyxVQUFVLEVBQUUsYUFBYSxFQUFFLE1BQU0sZ0JBQWdCLENBQUM7QUFFNUYsbUNBQW1DO0FBQ25DLDJFQUEyRTtBQUMzRSxNQUFNLFVBQVUsR0FBRyxDQUFDLEtBQUssRUFBRSxXQUFXLEVBQUUsUUFBUSxDQUFDLENBQUM7QUFDbEQsMkVBQTJFO0FBQzNFLE1BQU0sYUFBYSxHQUFHLElBQUksR0FBRyxDQUFDLENBQUMsMENBQTBDLENBQUMsQ0FBQyxDQUFDO0FBQzVFLHdFQUF3RTtBQUN4RSxNQUFNLFNBQVMsR0FBRyxJQUFJLEdBQUcsQ0FBQztJQUN4QjtRQUNFLGlEQUFpRDtRQUNqRCw2Q0FBNkM7S0FDOUM7Q0FDRixDQUFDLENBQUM7QUFDSCxNQUFNLFlBQVksR0FBRyxDQUFDLGlDQUFpQyxDQUFDLENBQUM7QUFDekQsaUNBQWlDO0FBRWpDLE9BQU8sQ0FBQyxHQUFHLENBQUMsT0FBTyxDQUFDLElBQUksQ0FBQyxDQUFDO0FBQzFCLE1BQU0sQ0FBQyxFQUFFLEFBQUQsRUFBRyxNQUFNLEVBQUUsYUFBYSxDQUFDLEdBQUcsT0FBTyxDQUFDLElBQUksQ0FBQztBQUNqRCxNQUFNLGNBQWMsR0FBRyxJQUFJLENBQUMsSUFBSSxDQUFDLE1BQU0sRUFBRSxtQkFBbUIsRUFBRSxHQUFHLFVBQVUsQ0FBQyxDQUFDO0FBRTdFLDJFQUEyRTtBQUMzRSxNQUFNLGVBQWUsR0FBRyxhQUFhLENBQUMsU0FBUyxD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