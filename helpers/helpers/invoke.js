import { exec } from 'child_process';
import { promisify } from 'util';
const exec_p = promisify(exec);
export async function invoke(cmd) {
    const res = { error: '', stdout: '', stderr: '' };
    try {
        const { stdout, stderr } = await exec_p(cmd);
        res.stderr = stderr;
        res.stdout = stdout;
    }
    catch (e) {
        if (typeof e === 'object' &&
            e !== null &&
            typeof e['toString'] === 'function') {
            res.error = e.toString();
        }
        else {
            res.error = 'Non-object error type :o';
        }
    }
    return res;
}
