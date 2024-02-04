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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW52b2tlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vaGVscGVycy1zcmMvaGVscGVycy9pbnZva2UudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLElBQUksRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUNyQyxPQUFPLEVBQUUsU0FBUyxFQUFFLE1BQU0sTUFBTSxDQUFDO0FBRWpDLE1BQU0sTUFBTSxHQUFHLFNBQVMsQ0FBQyxJQUFJLENBQUMsQ0FBQztBQUUvQixNQUFNLENBQUMsS0FBSyxVQUFVLE1BQU0sQ0FDMUIsR0FBVztJQUVYLE1BQU0sR0FBRyxHQUFHLEVBQUUsS0FBSyxFQUFFLEVBQUUsRUFBRSxNQUFNLEVBQUUsRUFBRSxFQUFFLE1BQU0sRUFBRSxFQUFFLEVBQUUsQ0FBQztJQUNsRCxJQUFJO1FBQ0YsTUFBTSxFQUFFLE1BQU0sRUFBRSxNQUFNLEVBQUUsR0FBRyxNQUFNLE1BQU0sQ0FBQyxHQUFHLENBQUMsQ0FBQztRQUM3QyxHQUFHLENBQUMsTUFBTSxHQUFHLE1BQU0sQ0FBQztRQUNwQixHQUFHLENBQUMsTUFBTSxHQUFHLE1BQU0sQ0FBQztLQUNyQjtJQUFDLE9BQU8sQ0FBVSxFQUFFO1FBQ25CLElBQ0UsT0FBTyxDQUFDLEtBQUssUUFBUTtZQUNyQixDQUFDLEtBQUssSUFBSTtZQUNWLE9BQU8sQ0FBQyxDQUFDLFVBQVUsQ0FBQyxLQUFLLFVBQVUsRUFDbkM7WUFDQSxHQUFHLENBQUMsS0FBSyxHQUFHLENBQUMsQ0FBQyxRQUFRLEVBQUUsQ0FBQztTQUMxQjthQUFNO1lBQ0wsR0FBRyxDQUFDLEtBQUssR0FBRywwQkFBMEIsQ0FBQztTQUN4QztLQUNGO0lBQ0QsT0FBTyxHQUFHLENBQUM7QUFDYixDQUFDIn0=