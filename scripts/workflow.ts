/*
 * This is the 'student workflow' script.
 * See the readme.md file for what it's supposed to do.
 */
import { simpleGit } from 'simple-git';
import { Error, Menu, Sleep } from './helpers/menu';
import { GetBranchName } from './helpers/branch';
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
    ['Start work for the day', startWork],
    ['Finish work for the day', finishWork],
    // ['Configure stuff', configureStuff],
    ['Connect to the control hub', connect],
    ['Disconnect from control hub', disconnect],
    ['Launch the dashboard', launchDash],
    ['Quit', () => Promise.resolve(true)],
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
  /* const pullRes = */ await git.pull();
  // console.log(pullRes);
  // Get the name for the new branch
  const branchName = await GetBranchName();
  if (typeof branchName !== 'string') {
    return false;
  }
  // Let's create & configure the branch
  /* const coRes = */ await git.checkout(['-b', branchName]);
  // console.log(coRes);
  // And now push it so it's wired up properly
  const pushRes = await git.push(['-u', 'origin', branchName]);
  // A little sanity check for the new branch
  if (
    pushRes.pushed.length !== 1 ||
    !pushRes.pushed[0].branch ||
    !pushRes.pushed[0].new ||
    pushRes.pushed[0].alreadyUpdated ||
    pushRes.pushed[0].deleted
  ) {
    console.log(pushRes);
    return Error('Unexpected result. Ask for help please.');
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

async function finishWork(): Promise<boolean> {
  // This should:
  // - Commit any outstanding work
  // - Check for any unadded files
  // - Make sure we've got network access
  // - Format code and add the commit if there were changes
  // - Push the code to github
  // - Try to build?
  // - If the build succeeded, offer to open a PR

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

  // Push the code
  const pullRes = await git.push();

  // Finally, pop up the page for a pull request
  if (pullRes.repo && pullRes.update && pullRes.update.head.local) {
    const branch = pullRes.update.head.local.replace(/.*\/([^\/]+)$/, '$1');
    await invoke(`yarn open ${pullRes.repo}/compare/${branch}?expand=1`);
  }
  return false;
}

// Nothign in here yet...
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
