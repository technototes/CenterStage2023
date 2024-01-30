// This is JS because it's invoked from gradle, and I'm trying
// to avoid a build step for these scripts on the student machine.

import * as fs from 'fs';

console.log(process.argv);
fs.writeFileSync('/Users/freik/KBF.txt', process.argv.join('\n'));
