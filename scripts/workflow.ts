/* This is my workflow script. See the readme.md file for what it's supposed to do
 * This should be invokable by ts-node. I might migrate the other 2 scripts to ts-node
 * as well
 */
import { promises as dns } from 'dns';
import { networkInterfaces } from 'os';
import { simpleGit } from 'simple-git';
import { Error, Menu, Ask } from './menu';
import { Clean } from './cleaner';

const DEFAULT_BRANCH_NAME = 'main';
const git = simpleGit();

// Gets an array of interface and ip address pairs
function getAddresses(): [string, string][] {
  const nets = networkInterfaces();
  const results: [string, string][] = [];
  for (const name of Object.keys(nets)) {
    if (nets[name] === undefined) {
      continue;
    }
    for (const net of nets[name]!) {
      // Skip over non-IPv4 and internal (i.e. 127.0.0.1) addresses
      // 'IPv4' is in Node <= 17, from 18 it's a number 4 or 6
      const familyV4Value = typeof net.family === 'string' ? 'IPv4' : 4;
      if (net.family === familyV4Value && !net.internal) {
        results.push([name, net.address]);
      }
    }
  }
  return results;
}

// Return true if we can find an address for github
// This may not work on networks with dns redirection...
async function hasGithubAccess(): Promise<boolean> {
  const res = await dns.resolve('github.com');
  if (!Array.isArray(res) || res.length === 0 || typeof res[0] !== 'string') {
    return Error('Nope');
  }
  const addr = res[0]
    .split('.')
    .map((expr) => Number.parseInt(expr, 10))
    .filter((val) => !isNaN(val) && val >= 0 && val <= 255);
  if (addr.length !== 4) {
    return Error('No');
  }
  return true;
}

// Return true if the only network address we find is a robot-like address
// TODO: This may not work for android phone usage (which changes 43 to 48?)
function onlyRobotConnection(): boolean {
  const addrs = getAddresses();
  return (
    addrs.filter((addr) => !addr[1].startsWith('192.168.43.')).length === 0
  );
}

const NAME_PROMPT = "What's your name?";
const TASK_PROMPT = "What are you working on today (1-2 words)?";

async function GetBranchName(): Promise<string | undefined> {
  let task = await Ask(TASK_PROMPT);
  let user = await Ask(NAME_PROMPT);
  const today = new Date();
  const yr = today.getFullYear();
  const mo = today.getMonth();
  const dy = today.getDate();
  let branch = '';
  let done = false;
  let abandon = true;
  async function nameChange() {
    user = await Ask(NAME_PROMPT);
    return true;
  }
  async function taskChange() {
    task = await Ask(TASK_PROMPT)
    return true;
  }
  async function allDone() {
    done = true;
    abandon = false;
    return Promise.resolve(true);
  }
  async function abort() {
    done = true;
    return Promise.resolve(true);
  }
  do {
    branch = `${yr}-${mo}-${dy}-${Clean(user)}-${Clean(task)}`;
    console.log();
    console.log(`Branch name: ${branch}`);
    console.log();
    await Menu("Does that look like a good branch name?", [
      ["Yup: Start coding!", allDone],
      ["No: Change my name.", nameChange],
      ["No: Change the work description", taskChange],
      ["No: Nevermind, just stop", abort]
    ]);
  } while (!done);
  return abandon ? undefined : branch;
}

async function workflow() {
  await Menu('What do you want to do?', [
    ['Start some work', startWork],
    ['Finish some work', finishWork],
    ['Configure stuff', configureStuff],
    ['Connect to the control hub', connect],
    ['Disconnect from control hub', disconnect],
    ['Launch the dashboard', launchDash],
    ['Call it a day', () => Promise.resolve(true)],
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

  // Repo dirty check:
  const status = await git.status();
  if (!status.isClean()) {
    return Error("You appear to have some work that isn't yet committed.");
  }

  // GitHub access check
  if (!(await hasGithubAccess())) {
    if (onlyRobotConnection()) {
      return Error(
        "It looks like you're connected to the robot. Please fix that before continuing.",
      );
    } else {
      return Error(
        'Unable to communicate with GitHub: Check your internet connection',
      );
    }
  }

  // Check out main
  console.log(await git.checkout(DEFAULT_BRANCH_NAME));
  const cur = await git.branch();
  if (cur.current !== DEFAULT_BRANCH_NAME) {
    return Error(`Unable to check out the ${DEFAULT_BRANCH_NAME} branch.`);
  }
  // Pull from github
  const res = await git.pull();
  console.log(res);
  // Get the name for the new branch
  const branchName = await GetBranchName();

  return false;
}

async function finishWork(): Promise<boolean> {
  // This should:
  // - Commit any outstanding work
  // - Make sure we've got network access
  // - Format code and add the commit if there were changes
  // - Push the code to github
  // - Try to build
  // - If the build succeeded, offer to open a PR
  // Repo dirty check:
  const status = await git.status();
  if (!status.isClean()) {
    console.error("You appear to have some work that isn't yet committed!");
    return false;
  }
  return Promise.resolve(false);
}

async function configureStuff(): Promise<boolean> {
  // This should:
  // Add my silly things to .gitconfig
  console.log('Configuring stuff');
  await hasGithubAccess();
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
