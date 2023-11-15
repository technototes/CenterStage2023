import { Ask, Menu } from './menu';

export function Clean(file: string): string {
  let res = file;
  // First, yoink diacriticals:
  res = res.normalize('NFD').replace(/\p{Diacritic}/gu, '');
  // Next, remove all non alpha-numeric stuff:
  res = res.replace(/[^0-9a-zA-Z]/g, '_');
  // Collapse multiple __'s into a single _
  res = res.replace(/__+/g, '_');
  // And trim initial/trailing underscores:
  return res.replace(/^_*/, '').replace(/_*$/, '');
}

function zpad(num: number): string {
  return num < 10 ? '0' + num.toString() : num.toString();
}

const NAME_PROMPT = "What's your user or name? ";
const TASK_PROMPT = 'What are you working on today (1-3 words)? ';

export async function GetBranchName(): Promise<string | undefined> {
  let task = await Ask(TASK_PROMPT);
  let user = await Ask(NAME_PROMPT);
  const today = new Date();
  const yr = today.getFullYear();
  const mo = today.getMonth() + 1;
  const dy = today.getDate();
  let branch = '';
  let done = false;
  let abandon = true;
  async function nameChange() {
    user = await Ask(NAME_PROMPT);
    return true;
  }
  async function taskChange() {
    task = await Ask(TASK_PROMPT);
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
    branch = `${Clean(user)}-${yr}.${zpad(mo)}.${zpad(dy)}-${Clean(task)}`;
    await Menu(`Does "${branch}" look like a good branch name?`, [
      ['Yup: Start coding!', allDone],
      ['No: Change my name.', nameChange],
      ['No: Change the work description', taskChange],
      ['No: Nevermind, just stop', abort],
    ]);
  } while (!done);
  return abandon ? undefined : branch;
}
