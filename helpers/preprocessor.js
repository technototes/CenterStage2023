import * as fs from 'fs/promises';
import * as path from 'path';
import { BaseJavaCstVisitorWithDefaults, parse, } from 'java-parser';
import { hasField, isNonNullable } from '@freik/typechk';
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
let curFile = '';
class AutoConstVisitor extends BaseJavaCstVisitorWithDefaults {
    constructor() {
        super();
        this.output = [];
        this.validateVisitor();
    }
    maybeVisit(field, name) {
        if (!isNonNullable(field))
            return;
        if (name)
            console.log(`// { // ${name}`);
        this.visit(field);
        if (name)
            console.log(`// } // ${name}`);
    }
    mustVisit(obj, name) {
        if (isNonNullable(obj)) {
            this.maybeVisit(obj, name);
        }
        else {
            throw new Error(`Missing required child element ${name ?? ''}`);
        }
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
        this.mustVisit(ctx.normalClassDeclaration);
    }
    classBodyDeclaration(ctx, param) {
        unsupported('constructorDeclaration', ctx);
        unsupported('instanceInitializer', ctx);
        unsupported('staticInitializer', ctx);
        this.mustVisit(ctx.classMemberDeclaration);
    }
    classMemberDeclaration(ctx, param) {
        unsupported('methodDeclaration', ctx);
        unsupported('interfaceDeclaration', ctx);
        this.maybeVisit(ctx.fieldDeclaration);
        this.maybeVisit(ctx.classDeclaration);
    }
    fieldDeclaration(ctx, param) {
        // console.log("field: ", ctx);
        // TODO: Validate field modifiers "public static"
        this.maybeVisit(ctx.fieldModifier);
        this.mustVisit(ctx.variableDeclaratorList);
    }
    variableDeclaratorList(ctx, param) {
        // console.log("varDeclList: ", ctx);
        this.mustVisit(ctx.variableDeclarator);
    }
    variableDeclarator(ctx, param) {
        // console.log("varDecl: ", ctx);
        this.mustVisit(ctx.variableDeclaratorId);
        this.mustVisit(ctx.variableInitializer);
    }
    variableDeclaratorId(ctx, param) {
        if (required(ctx.Identifier, 'Unsupported Identifier-less varDeclID')) {
            console.log('varDeclId: ', ctx.Identifier[0].image);
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
        this.mustVisit(ctx.lambdaParameters);
        this.mustVisit(ctx.lambdaBody);
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
        this.maybeVisit(ctx.primarySuffix);
        this.mustVisit(ctx.primaryPrefix);
    }
    primaryPrefix(ctx, param) {
        this.maybeVisit(ctx.newExpression);
        this.maybeVisit(ctx.fqnOrRefType);
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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicHJlcHJvY2Vzc29yLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vaGVscGVycy1zcmMvcHJlcHJvY2Vzc29yLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sS0FBSyxFQUFFLE1BQU0sYUFBYSxDQUFDO0FBQ2xDLE9BQU8sS0FBSyxJQUFJLE1BQU0sTUFBTSxDQUFDO0FBQzdCLE9BQU8sRUFDTCw4QkFBOEIsRUEwQjlCLEtBQUssR0FDTixNQUFNLGFBQWEsQ0FBQztBQUNyQixPQUFPLEVBQUUsUUFBUSxFQUFxQyxhQUFhLEVBQUUsTUFBTSxnQkFBZ0IsQ0FBQztBQUU1RixtQ0FBbUM7QUFDbkMsMkVBQTJFO0FBQzNFLE1BQU0sVUFBVSxHQUFHLENBQUMsS0FBSyxFQUFFLFdBQVcsRUFBRSxRQUFRLENBQUMsQ0FBQztBQUNsRCwyRUFBMkU7QUFDM0UsTUFBTSxhQUFhLEdBQUcsSUFBSSxHQUFHLENBQUMsQ0FBQywwQ0FBMEMsQ0FBQyxDQUFDLENBQUM7QUFDNUUsd0VBQXdFO0FBQ3hFLE1BQU0sU0FBUyxHQUFHLElBQUksR0FBRyxDQUFDO0lBQ3hCO1FBQ0UsaURBQWlEO1FBQ2pELDZDQUE2QztLQUM5QztDQUNGLENBQUMsQ0FBQztBQUNILE1BQU0sWUFBWSxHQUFHLENBQUMsaUNBQWlDLENBQUMsQ0FBQztBQUN6RCxpQ0FBaUM7QUFFakMsT0FBTyxDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsSUFBSSxDQUFDLENBQUM7QUFDMUIsTUFBTSxDQUFDLEVBQUUsQUFBRCxFQUFHLE1BQU0sRUFBRSxhQUFhLENBQUMsR0FBRyxPQUFPLENBQUMsSUFBSSxDQUFDO0FBQ2pELE1BQU0sY0FBYyxHQUFHLElBQUksQ0FBQyxJQUFJLENBQUMsTUFBTSxFQUFFLG1CQUFtQixFQUFFLEdBQUcsVUFBVSxDQUFDLENBQUM7QUFFN0UsMkVBQTJFO0FBQzNFLE1BQU0sZUFBZSxHQUFHLGFBQWEsQ0FBQyxTQUFTLENBQUMsQ0FBQyxFQUFFLGFBQWEs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