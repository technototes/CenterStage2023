/* This is my workflow script. See the readme.md file for what it's supposed to do
 * This should be invokable by ts-node. I might migrate the other 2 scripts to ts-node
 * as well
 */
import { networkInterfaces } from 'os';
import readline from 'readline';
import { simpleGit } from 'simple-git';

const git = simpleGit();

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});


function getAddresses(): { [key: string]: string[] } {

  const nets = networkInterfaces();
  const results: { [key: string]: string[] } = {};

  for (const name of Object.keys(nets)) {
    if (nets[name] === undefined) {
      continue;
    }
    for (const net of nets[name]!) {
      // Skip over non-IPv4 and internal (i.e. 127.0.0.1) addresses
      // 'IPv4' is in Node <= 17, from 18 it's a number 4 or 6
      const familyV4Value = typeof net.family === 'string' ? 'IPv4' : 4
      if (net.family === familyV4Value && !net.internal) {
        if (!results[name]) {
          results[name] = [];
        }
        results[name].push(net.address);
      }
    }
  }
  return results;
}


type MenuItem = { prompt: string; func: () => Promise<boolean> };
function mnu(prompt: string, func: () => Promise<boolean>): MenuItem {
  return { prompt, func };
}

const ask = (query: string): Promise<string> =>
  new Promise((resolve) => rl.question(query, resolve));

// Display a menu with a header, and run the function for the menu item selected
async function menu(header: string, menu: MenuItem[]): Promise<void> {
  let done = false;
  while (!done) {
    const bar = '*'.repeat(header.length + 4);
    console.log();
    console.log(bar);
    console.log('*', header, '*');
    console.log(bar);
    console.log();
    menu.forEach((val: MenuItem, i: number) =>
      console.log(`${i + 1}: ${val.prompt}`),
    );
    console.log();
    const res = await ask('Please select an option: ');
    const cleaned = res.trim();
    const opt = Number.parseInt(cleaned, 10);
    if (isNaN(opt) || opt < 1 || opt > menu.length) {
      console.error(`Please enter a number from 1 to ${menu.length}`);
    } else {
      done = await menu[opt - 1].func();
    }
  }
}

async function workflow() {
  await menu('What do you want to do?', [
    mnu('Start some work', startWork),
    mnu('Finish some work', finishWork),
    mnu('Configure stuff', configureStuff),
    mnu('Connect to the control hub', connect),
    mnu('Disconnect from control hub', disconnect),
    mnu('launch the dashboar', launchDash),
    mnu('Call it a day', () => Promise.resolve(true)),
  ]);
}

async function startWork(): Promise<boolean> {
  // This should:
  // - Ensure the repo isn't dirty
  // - Make sure we've got network access
  // - Check out main
  // - Pull from main
  // - Create a new branch for the work
  // - Open up Android Studio
  console.log('Starting work');
  const status = await git.status();
  if (!status.isClean()) {
    console.error("You appear to have some work that isn't yet commited.");
    return false;
  }
  const addrs = getAddresses();
  console.log(addrs);
  return false
}

async function finishWork(): Promise<boolean> {
  // This should:
  // - Commit any outstanding work
  // - Make sure we've got network access
  // - Format code and add the commit if there were changes
  // - Push the code to github
  // - Try to build
  // - If the build succeeded, offer to open a PR
  console.log('Finishing work');
  return Promise.resolve(false);
}

async function configureStuff(): Promise<boolean> {
  // This should:
  // Add my silly things to .gitconfig
  console.log('Configuring stuff');
  return Promise.resolve(false);
}

async function connect(): Promise<boolean> {
  // yarn connect
  console.log('connect');
  return Promise.resolve(false);
}

async function disconnect(): Promise<boolean> {
  // yarn discall
  console.log('disconnect');
  return Promise.resolve(false);
}

async function launchDash(): Promise<boolean> {
  // yarn dash
  console.log('launch dash');
  return Promise.resolve(false);
}

workflow()
  .catch((err) => console.error(err))
  .finally(() => {
    // console.log('All Done');
    process.exit(0);
  });
