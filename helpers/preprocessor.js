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
                content += multiple ? `[${i}]:` : '->';
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
        codeSpit(" }");
    }
    typeIdentifier(ctx, param) {
        codeAdd(ctx.Identifier.map(tok => tok.image).join('.'));
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
        // This is a list
        this.maybeVisit(ctx.fieldModifier);
        const modifiers = getContent(ctx.fieldModifier);
        console.log(`The modifiers: "${modifiers}"`);
        this.mustVisit(ctx.variableDeclaratorList);
    }
    variableDeclaratorList(ctx, param) {
        // console.log("varDeclList: ", ctx);
        // This is a list
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
        this.maybeVisit(ctx.fqnOrRefTypePartRest);
    }
    newExpression(ctx, param) {
        // console.log('new', ctx);
        this.maybeVisit(ctx.unqualifiedClassInstanceCreationExpression);
    }
    unqualifiedClassInstanceCreationExpression(ctx, param) {
        this.mustVisit(ctx.classOrInterfaceTypeToInstantiate);
        this.maybeVisit(ctx.argumentList);
    }
    argumentList(ctx, param) {
        this.mustVisit(ctx.expression);
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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicHJlcHJvY2Vzc29yLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vaGVscGVycy1zcmMvcHJlcHJvY2Vzc29yLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sS0FBSyxFQUFFLE1BQU0sYUFBYSxDQUFDO0FBQ2xDLE9BQU8sS0FBSyxJQUFJLE1BQU0sTUFBTSxDQUFDO0FBQzdCLE9BQU8sRUFFTCw4QkFBOEIsRUE4QjlCLEtBQUssR0FDTixNQUFNLGFBQWEsQ0FBQztBQUNyQixPQUFPLEVBQ0wsU0FBUyxFQUNULFlBQVksRUFDWixRQUFRLEVBQ1IsWUFBWSxFQUNaLFdBQVcsRUFDWCxPQUFPLEVBR1AsYUFBYSxFQUNiLFFBQVEsR0FDVCxNQUFNLGdCQUFnQixDQUFDO0FBRXhCLG1DQUFtQztBQUNuQywyRUFBMkU7QUFDM0UsTUFBTSxVQUFVLEdBQUcsQ0FBQyxLQUFLLEVBQUUsV0FBVyxFQUFFLFFBQVEsQ0FBQyxDQUFDO0FBQ2xELDJFQUEyRTtBQUMzRSxNQUFNLGFBQWEsR0FBRyxJQUFJLEdBQUcsQ0FBQyxDQUFDLDBDQUEwQyxDQUFDLENBQUMsQ0FBQztBQUM1RSx3RUFBd0U7QUFDeEUsTUFBTSxTQUFTLEdBQUcsSUFBSSxHQUFHLENBQUM7SUFDeEI7UUFDRSxpREFBaUQ7UUFDakQsNkNBQTZDO0tBQzlDO0lBQ0Q7UUFDRSxnREFBZ0Q7UUFDaEQsNkNBQTZDO0tBQzlDO0NBQ0YsQ0FBQyxDQUFDO0FBQ0gsTUFBTSxZQUFZLEdBQUcsQ0FBQyxpQ0FBaUMsQ0FBQyxDQUFDO0FBQ3pELGlDQUFpQztBQUVqQyxPQUFPLENBQUMsR0FBRyxDQUFDLE9BQU8sQ0FBQyxJQUFJLENBQUMsQ0FBQztBQUMxQixNQUFNLENBQUMsRUFBRSxBQUFELEVBQUcsTUFBTSxFQUFFLGFBQWEsQ0FBQyxHQUFHLE9BQU8sQ0FBQyxJQUFJLENBQUM7QUFDakQsTUFBTSxjQUFjLEdBQUcsSUFBSSxDQUFDLElBQUksQ0FBQyxNQUFNLEVBQUUsbUJBQW1C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