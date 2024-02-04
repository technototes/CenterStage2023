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