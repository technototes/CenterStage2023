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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW52b2tlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vaGVscGVycy1zcmMvaGVscGVycy9pbnZva2UudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLElBQUksRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUNyQyxPQUFPLEVBQUUsU0FBUyxFQUFFLE1BQU0sTUFBTSxDQUFDO0FBRWpDLE1BQU0sTUFBTSxHQUFHLFNBQVMsQ0FBQyxJQUFJLENBQUMsQ0FBQztBQUUvQixNQUFNLENBQUMsS0FBSyxVQUFVLE1BQU0sQ0FDMUIsR0FBVztJQUVYLE1BQU0sR0FBRyxHQUFHLEVBQUUsS0FBSyxFQUFFLEVBQUUsRUFBRSxNQUFNLEVBQUUsRUFBRSxFQUFFLE1BQU0sRUFBRSxFQUFFLEVBQUUsQ0FBQztJQUNsRCxJQUFJLENBQUM7UUFDSCxNQUFNLEVBQUUsTUFBTSxFQUFFLE1BQU0sRUFBRSxHQUFHLE1BQU0sTUFBTSxDQUFDLEdBQUcsQ0FBQyxDQUFDO1FBQzdDLEdBQUcsQ0FBQyxNQUFNLEdBQUcsTUFBTSxDQUFDO1FBQ3BCLEdBQUcsQ0FBQyxNQUFNLEdBQUcsTUFBTSxDQUFDO0lBQ3RCLENBQUM7SUFBQyxPQUFPLENBQVUsRUFBRSxDQUFDO1FBQ3BCLElBQ0UsT0FBTyxDQUFDLEtBQUssUUFBUTtZQUNyQixDQUFDLEtBQUssSUFBSTtZQUNWLE9BQU8sQ0FBQyxDQUFDLFVBQVUsQ0FBQyxLQUFLLFVBQVUsRUFDbkMsQ0FBQztZQUNELEdBQUcsQ0FBQyxLQUFLLEdBQUcsQ0FBQyxDQUFDLFFBQVEsRUFBRSxDQUFDO1FBQzNCLENBQUM7YUFBTSxDQUFDO1lBQ04sR0FBRyxDQUFDLEtBQUssR0FBRywwQkFBMEIsQ0FBQztRQUN6QyxDQUFDO0lBQ0gsQ0FBQztJQUNELE9BQU8sR0FBRyxDQUFDO0FBQ2IsQ0FBQyJ9