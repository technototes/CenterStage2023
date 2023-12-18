/*
 * This is the 'student workflow' script.
 * See the readme.md file for what it's supposed to do.
 */
import { Error, Menu } from './helpers/menu';
import { invoke } from './helpers/invoke';
import { hasGithubAccess, anyRobotConnection } from './helpers/connectivity';
import { ResumeWork, StartWork } from './helpers/beginWork';
import { FinishWork, StopWork } from './helpers/endWork';

async function workflow() {
  console.clear();
  await Menu('What do you want to do?', [
    StartWork,
    ResumeWork,
    FinishWork,
    StopWork,
    // ['Configure stuff', configureStuff],
    ['Connect to the control hub', connect],
    ['Disconnect from control hub', disconnect],
    ['Launch the dashboard', launchDash],
    ['Quit', () => Promise.resolve(true)],
  ]);
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
