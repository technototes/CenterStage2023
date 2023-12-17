/*
 * This is the 'student workflow' script.
 * See the readme.md file for what it's supposed to do.
 */
import { PushResult, simpleGit } from 'simple-git';
import { Error, Menu, Sleep } from './helpers/menu';
import {
  GetBranchName,
  PickBranchToContinue,
  ReadBranchName,
} from './helpers/branch';
import { invoke } from './helpers/invoke';
import {
  hasGithubAccess,
  onlyRobotConnection,
  anyRobotConnection,
} from './helpers/connectivity';

const DEFAULT_BRANCH_NAME = 'main';
const git = simpleGit();

async function workflow() {
  console.clear();
  await Menu('What do you want to do?', [
    ['Start new work for the day', startWork],
    ['Resume work from previous day', resumeWork],
    ['Finish work: ready for merging', finishWork],
    ['Finish for the day (not yet ready for merging)', stopWork],
    // ['Configure stuff', configureStuff],
    ['Connect to the control hub', connect],
    ['Disconnect from control hub', disconnect],
    ['Launch the dashboard', launchDash],
    ['Quit', () => Promise.resolve(true)],
  ]);
}

// Helper for start & resumt
async function getStarted(): Promise<boolean> {
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
  /* const pullRes = */ await git.pull();
  // console.log(pullRes);
  return true;
}

async function startWork(): Promise<boolean> {
  // This should:
  // --- Begin getStarted stuff
  // - Ensure the repo isn't dirty
  // - Make sure we've got network access
  // - Check out main
  // - Pull from main
  // --- End getStarted stuff
  // - Create a new branch for the work
  // - Open up Android Studio

  if ((await getStarted()) === false) {
    return false;
  }
  // Get the name for the new branch
  const branchName = await GetBranchName();
  if (typeof branchName !== 'string') {
    return false;
  }
  // Let's create the branch
  /* const coRes = */ await git.checkout(['-b', branchName]);
  // console.log(coRes);
  console.log(
    "You're ready to code! Come back to this window when you're done.",
  );
  await Sleep(3000);
  // Maybe open android studio automatically?
  return false;
}

async function resumeWork(): Promise<boolean> {
  // This should:

  // --- Begin getStarted stuff
  // - Ensure the repo isn't dirty
  // - Make sure we've got network access
  // - Check out main
  // - Pull from main
  // --- End getStarted stuff

  // - Pull all branch names
  // - Ask which branch to check out
  // - Check out the branch to continue
  // - Ensure it's up-to-date (pull)
  // - Open up Android Studio

  if ((await getStarted()) === false) {
    return false;
  }

  // Continue from "pull all branch names"
  if (await PickBranchToContinue(git)) {
    return true;
  }
  console.log(
    "You're ready to code! Come back to this window when you're done.",
  );
  await Sleep(3000);
  // Maybe open android studio automatically?
  return false;
}

async function addFiles(files: string[], message: string): Promise<boolean> {
  console.log(await git.add(files).commit(message));
  return true;
}

// Helper for stop & finish:
async function completeWork(): Promise<false | PushResult> {
  // Repo dirty check:
  const status = await git.status();
  if (!status.isClean()) {
    return Error("You appear to have some work that isn't yet committed!");
  }

  // Un-added file check
  if (status.not_added.length !== 0) {
    let abort = false;
    console.log('These files:');
    console.log(status.not_added);
    await Menu('What should be done with those untracked files?', [
      [
        "Ignore them: It's fine. Trust me. I know what I'm doing.",
        () => Promise.resolve(true),
      ],
      [
        'Please add them to the repository.',
        () => addFiles(status.not_added, 'Oops, missed some files!'),
      ],
      [
        'Abort! Abort! Abort!',
        () => {
          abort = true;
          return Promise.resolve(true);
        },
      ],
    ]);
    if (abort) {
      return false;
    }
  }

  // Check for network access
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

  // Run the code formatting
  await invoke('yarn format');
  const fmtStat = await git.status();
  if (!fmtStat.isClean()) {
    console.log('Some files were re-formatted. Commiting the change.');
    await addFiles(fmtStat.modified, 'Auto-formatted files');
  }

  const curBranchNameOrErr = await ReadBranchName(git);
  if (curBranchNameOrErr === false) {
    console.log("Couldn't get the curent branch name.");
    return false;
  }
  // Push the code (Wire it  properly)
  return await git.push(['-u', 'origin', curBranchNameOrErr]);
}

async function stopWork(): Promise<boolean> {
  // This should:
  // - Commit any outstanding work
  // - Check for any unadded files
  // - Make sure we've got network access
  // - Format code and add the commit if there were changes
  // - Push the code to github
  // - Check out main
  const failureOrPullRes = await completeWork();

  if (failureOrPullRes === false) {
    return false;
  }

  // Check out main
  console.log(await git.checkout(DEFAULT_BRANCH_NAME));
  const cur = await git.branch();
  if (cur.current !== DEFAULT_BRANCH_NAME) {
    return Error(`Unable to check out the ${DEFAULT_BRANCH_NAME} branch.`);
  }
  return false;
}

async function finishWork(): Promise<boolean> {
  // This should:
  // - Commit any outstanding work
  // - Check for any unadded files
  // - Make sure we've got network access
  // - Format code and add the commit if there were changes
  // - Push the code to github
  // - Try to build?
  // - If the build succeeded, offer to open a PR

  const failureOrPullRes = await completeWork();

  if (failureOrPullRes === false) {
    return false;
  }

  const branch = await ReadBranchName(git);
  if (branch === false) {
    console.log('Some weird error: Ask for help.');
  }
  const url = `GetGitHubUrlFromRepo(failureOrPullRes.repo)/compare/${DEFAULT_BRANCH_NAME}...${branch}`;
  switch (process.platform) {
    case 'win32':
      await invoke(`start "" "${url}"`);
      break;
    case 'darwin':
      await invoke('open ' + url);
      break;
    case 'linux':
      await invoke('xdg-open ' + url);
      break;
  }
  return false;
}

// Nothing in here yet...
async function configureStuff(): Promise<boolean> {
  // This should:
  // Add my silly things to .gitconfig
  console.log('Configuring stuff');
  await hasGithubAccess();
  return Promise.resolve(false);
}

async function connect(): Promise<boolean> {
  if (!anyRobotConnection()) {
    return Error("You don't appear to be connected to a robot.");
  }
  const res = await invoke('yarn connect');
  console.log(res.stdout);
  console.log(res.stderr);
  return false;
}

async function disconnect(): Promise<boolean> {
  const res = await invoke('yarn discall');
  console.log(res.stdout);
  console.log(res.stderr);
  return false;
}

async function launchDash(): Promise<boolean> {
  const res = await invoke('yarn dash');
  console.log(res.stdout);
  console.log(res.stderr);
  return false;
}

workflow()
  .catch((err) => console.error(err))
  .finally(() => {
    // console.log('All Done');
    process.exit(0);
  });
