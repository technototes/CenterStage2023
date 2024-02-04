/*
 * This script inverts all comment lines in a set of files that end with
 * '// FLIP: TechnoLibLocal' comment (The "TechnoLibLocal" is configurable).
 *
 * For TechnoLibLocal, this lets you add a subdirectory "TechnoLib" (as a git
 * subtree, submodule, or just a copy) that contains TechnoLib instead of
 * pulling down the latest release from jitpack.io through Maven or whatever
 * that remote repo is.
 *
 * For BUILD ALL BOTS, it toggles including not just the competition bots, but
 * the ForTeaching, RoadRunner Quick Start and any other 'bot' subdirs that we
 * may not want to build in the heat of competition work.
 *
 * "yarn libflip" will, from a normal repo, enable you to build & link with
 * a local copy of TechnoLib in <root>\TechnoLib. Once you're done, run
 * "yarn libflip" again, and it will restore the dependency on the publicly
 * released copy of TechnoLib
 */
import { promises } from 'fs';
import { argv } from 'process';
const { readFile, writeFile } = promises;
// This is a map of keys (the argument to call flip.js with) to objects that
// are a name (the tag at the end of the comment) and an array of files to
// scan & process
const fileList = new Map([
    [
        'lib',
        {
            name: 'TechnoLibLocal',
            files: [
                'LearnBot/build.gradle',
                'Sixteen750/build.gradle',
                'Twenty403/build.gradle',
                'build.dependencies.gradle',
                'settings.gradle',
            ],
        },
    ],
    [
        'bot',
        { name: 'BUILD ALL BOTS', files: ['settings.gradle', 'build.gradle'] },
    ],
]);
// For any line that ends with '// FLIP: id',
// toggle the line comment 'status'
function toggleLine(lineFull, str) {
    const commentMarker = '// FLIP: ' + str;
    const line = lineFull.trimEnd();
    // If the line doesn't end with the comment marker, don't change it at all
    if (!line.endsWith(commentMarker)) {
        return line;
    }
    if (line.trimStart().startsWith('//')) {
        // If the line starts with a comment,
        // remove the comment at the beginning of the line
        return line.replace(/^( *)\/\/ */, '$1');
    }
    else {
        // If the line does *not* start with a comment,
        // add the comment at the beginning of the line
        return line.replace(/^( *)([^ ])/, '$1// $2');
    }
}
// Read the file, flip the comments for lines with markers, the write it back
async function toggleFile(file, str) {
    try {
        const contents = await readFile(file, 'utf-8');
        const resultArray = contents.split('\n');
        const toggled = resultArray.map((elem) => toggleLine(elem, str));
        await writeFile(file, toggled.join('\n'));
    }
    catch (e) {
        // Some file access problem :(
        console.error('Problem with dealing with file:', file);
        console.error(e);
    }
}
async function toggleLinesWithComments(arg) {
    const elem = fileList.get(arg);
    if (elem === undefined) {
        throw Error("Only use this script with the 'lib' or 'bot' argument");
    }
    let { name: str, files } = elem;
    for (let filename of files) {
        await toggleFile(filename, str);
    }
}
// Call our script...
toggleLinesWithComments(argv[2])
    // Reporting errors
    .catch((err) => console.error(err))
    .finally(() => console.log('Processing complete'));
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZmxpcC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uL2hlbHBlcnMtc3JjL2ZsaXAudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Ozs7Ozs7Ozs7Ozs7Ozs7O0dBaUJHO0FBRUgsT0FBTyxFQUFFLFFBQVEsRUFBRSxNQUFNLElBQUksQ0FBQztBQUM5QixPQUFPLEVBQUUsSUFBSSxFQUFFLE1BQU0sU0FBUyxDQUFDO0FBRS9CLE1BQU0sRUFBRSxRQUFRLEVBQUUsU0FBUyxFQUFFLEdBQUcsUUFBUSxDQUFDO0FBRXpDLDRFQUE0RTtBQUM1RSwwRUFBMEU7QUFDMUUsaUJBQWlCO0FBQ2pCLE1BQU0sUUFBUSxHQUFHLElBQUksR0FBRyxDQUFDO0lBQ3ZCO1FBQ0UsS0FBSztRQUNMO1lBQ0UsSUFBSSxFQUFFLGdCQUFnQjtZQUN0QixLQUFLLEVBQUU7Z0JBQ0wsdUJBQXVCO2dCQUN2Qix5QkFBeUI7Z0JBQ3pCLHdCQUF3QjtnQkFDeEIsMkJBQTJCO2dCQUMzQixpQkFBaUI7YUFDbEI7U0FDRjtLQUNGO0lBQ0Q7UUFDRSxLQUFLO1FBQ0wsRUFBRSxJQUFJLEVBQUUsZ0JBQWdCLEVBQUUsS0FBSyxFQUFFLENBQUMsaUJBQWlCLEVBQUUsY0FBYyxDQUFDLEVBQUU7S0FDdkU7Q0FDRixDQUFDLENBQUM7QUFFSCw2Q0FBNkM7QUFDN0MsbUNBQW1DO0FBQ25DLFNBQVMsVUFBVSxDQUFDLFFBQWdCLEVBQUUsR0FBVztJQUMvQyxNQUFNLGFBQWEsR0FBRyxXQUFXLEdBQUcsR0FBRyxDQUFDO0lBQ3hDLE1BQU0sSUFBSSxHQUFHLFFBQVEsQ0FBQyxPQUFPLEVBQUUsQ0FBQztJQUNoQywwRUFBMEU7SUFDMUUsSUFBSSxDQUFDLElBQUksQ0FBQyxRQUFRLENBQUMsYUFBYSxDQUFDLEVBQUU7UUFDakMsT0FBTyxJQUFJLENBQUM7S0FDYjtJQUNELElBQUksSUFBSSxDQUFDLFNBQVMsRUFBRSxDQUFDLFVBQVUsQ0FBQyxJQUFJLENBQUMsRUFBRTtRQUNyQyxxQ0FBcUM7UUFDckMsa0RBQWtEO1FBQ2xELE9BQU8sSUFBSSxDQUFDLE9BQU8sQ0FBQyxhQUFhLEVBQUUsSUFBSSxDQUFDLENBQUM7S0FDMUM7U0FBTTtRQUNMLCtDQUErQztRQUMvQywrQ0FBK0M7UUFDL0MsT0FBTyxJQUFJLENBQUMsT0FBTyxDQUFDLGFBQWEsRUFBRSxTQUFTLENBQUMsQ0FBQztLQUMvQztBQUNILENBQUM7QUFFRCw2RUFBNkU7QUFDN0UsS0FBSyxVQUFVLFVBQVUsQ0FBQyxJQUFZLEVBQUUsR0FBVztJQUNqRCxJQUFJO1FBQ0YsTUFBTSxRQUFRLEdBQUcsTUFBTSxRQUFRLENBQUMsSUFBSSxFQUFFLE9BQU8sQ0FBQyxDQUFDO1FBQy9DLE1BQU0sV0FBVyxHQUFHLFFBQVEsQ0FBQyxLQUFLLENBQUMsSUFBSSxDQUFDLENBQUM7UUFDekMsTUFBTSxPQUFPLEdBQUcsV0FBVyxDQUFDLEdBQUcsQ0FBQyxDQUFDLElBQUksRUFBRSxFQUFFLENBQUMsVUFBVSxDQUFDLElBQUksRUFBRSxHQUFHLENBQUMsQ0FBQyxDQUFDO1FBQ2pFLE1BQU0sU0FBUyxDQUFDLElBQUksRUFBRSxPQUFPLENBQUMsSUFBSSxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUM7S0FDM0M7SUFBQyxPQUFPLENBQUMsRUFBRTtRQUNWLDhCQUE4QjtRQUM5QixPQUFPLENBQUMsS0FBSyxDQUFDLGlDQUFpQyxFQUFFLElBQUksQ0FBQyxDQUFDO1FBQ3ZELE9BQU8sQ0FBQyxLQUFLLENBQUMsQ0FBQyxDQUFDLENBQUM7S0FDbEI7QUFDSCxDQUFDO0FBRUQsS0FBSyxVQUFVLHVCQUF1QixDQUFDLEdBQVc7SUFDaEQsTUFBTSxJQUFJLEdBQUcsUUFBUSxDQUFDLEdBQUcsQ0FBQyxHQUFHLENBQUMsQ0FBQztJQUMvQixJQUFJLElBQUksS0FBSyxTQUFTLEVBQUU7UUFDdEIsTUFBTSxLQUFLLENBQUMsdURBQXVELENBQUMsQ0FBQztLQUN0RTtJQUNELElBQUksRUFBRSxJQUFJLEVBQUUsR0FBRyxFQUFFLEtBQUssRUFBRSxHQUFHLElBQUksQ0FBQztJQUNoQyxLQUFLLElBQUksUUFBUSxJQUFJLEtBQUssRUFBRTtRQUMxQixNQUFNLFVBQVUsQ0FBQyxRQUFRLEVBQUUsR0FBRyxDQUFDLENBQUM7S0FDakM7QUFDSCxDQUFDO0FBRUQscUJBQXFCO0FBQ3JCLHVCQUF1QixDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsQ0FBQztJQUM5QixtQkFBbUI7S0FDbEIsS0FBSyxDQUFDLENBQUMsR0FBRyxFQUFFLEVBQUUsQ0FBQyxPQUFPLENBQUMsS0FBSyxDQUFDLEdBQUcsQ0FBQyxDQUFDO0tBQ2xDLE9BQU8sQ0FBQyxHQUFHLEVBQUUsQ0FBQyxPQUFPLENBQUMsR0FBRyxDQUFDLHFCQUFxQixDQUFDLENBQUMsQ0FBQyJ9