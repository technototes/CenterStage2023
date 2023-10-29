import readline from 'readline';

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

export const Ask = (query: string): Promise<string> =>
  new Promise((resolve) => rl.question(query, resolve));

// Display a menu with a header, and run the function for the menu item selected
export async function Menu(
  header: string,
  menu: [string, () => Promise<boolean>][],
): Promise<void> {
  let done = false;
  while (!done) {
    const bar = '*'.repeat(header.length + 4);
    console.log();
    console.log(bar);
    console.log('*', header, '*');
    console.log(bar);
    console.log();
    menu.forEach((val: [string, () => Promise<boolean>], i: number) =>
      console.log(`${i + 1}: ${val[0]}`),
    );
    console.log();
    const res = await Ask('Please select an option: ');
    const cleaned = res.trim();
    const opt = Number.parseInt(cleaned, 10);
    if (isNaN(opt) || opt < 1 || opt > menu.length) {
      console.error(`Please enter a number from 1 to ${menu.length}`);
    } else {
      done = await menu[opt - 1][1]();
    }
  }
}

export async function Error(message: string): Promise<boolean> {
  console.error('>>>> ');
  console.error('>>>>', message);
  console.error('>>>> ');
  function sleep(ms: number) {
    return new Promise((resolve) => {
      setTimeout(resolve, ms);
    });
  }
  await sleep(3000);
  return false;
}
