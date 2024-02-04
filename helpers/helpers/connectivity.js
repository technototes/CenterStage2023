import { promises as dns } from 'dns';
import { networkInterfaces } from 'os';
import { Error } from './menu.js';
// Gets an array of interface and ip address pairs
function getAddresses() {
    const nets = networkInterfaces();
    const results = [];
    for (const name of Object.keys(nets)) {
        if (nets[name] === undefined) {
            continue;
        }
        for (const net of nets[name]) {
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
export async function hasGithubAccess() {
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
export function onlyRobotConnection() {
    const addrs = getAddresses();
    return addrs.every((addr) => !addr[1].startsWith('192.168.43.'));
}
export function anyRobotConnection() {
    const addrs = getAddresses();
    return addrs.some((addr) => addr[1].startsWith('192.168.43.'));
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29ubmVjdGl2aXR5LmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vaGVscGVycy1zcmMvaGVscGVycy9jb25uZWN0aXZpdHkudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFFBQVEsSUFBSSxHQUFHLEVBQUUsTUFBTSxLQUFLLENBQUM7QUFDdEMsT0FBTyxFQUFFLGlCQUFpQixFQUFFLE1BQU0sSUFBSSxDQUFDO0FBQ3ZDLE9BQU8sRUFBRSxLQUFLLEVBQUUsTUFBTSxXQUFXLENBQUM7QUFFbEMsa0RBQWtEO0FBQ2xELFNBQVMsWUFBWTtJQUNuQixNQUFNLElBQUksR0FBRyxpQkFBaUIsRUFBRSxDQUFDO0lBQ2pDLE1BQU0sT0FBTyxHQUF1QixFQUFFLENBQUM7SUFDdkMsS0FBSyxNQUFNLElBQUksSUFBSSxNQUFNLENBQUMsSUFBSSxDQUFDLElBQUksQ0FBQyxFQUFFO1FBQ3BDLElBQUksSUFBSSxDQUFDLElBQUksQ0FBQyxLQUFLLFNBQVMsRUFBRTtZQUM1QixTQUFTO1NBQ1Y7UUFDRCxLQUFLLE1BQU0sR0FBRyxJQUFJLElBQUksQ0FBQyxJQUFJLENBQUUsRUFBRTtZQUM3Qiw2REFBNkQ7WUFDN0Qsd0RBQXdEO1lBQ3hELE1BQU0sYUFBYSxHQUFHLE9BQU8sR0FBRyxDQUFDLE1BQU0sS0FBSyxRQUFRLENBQUMsQ0FBQyxDQUFDLE1BQU0sQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQ2xFLElBQUksR0FBRyxDQUFDLE1BQU0sS0FBSyxhQUFhLElBQUksQ0FBQyxHQUFHLENBQUMsUUFBUSxFQUFFO2dCQUNqRCxPQUFPLENBQUMsSUFBSSxDQUFDLENBQUMsSUFBSSxFQUFFLEdBQUcsQ0FBQyxPQUFPLENBQUMsQ0FBQyxDQUFDO2FBQ25DO1NBQ0Y7S0FDRjtJQUNELE9BQU8sT0FBTyxDQUFDO0FBQ2pCLENBQUM7QUFDRCxtREFBbUQ7QUFDbkQsd0RBQXdEO0FBQ3hELE1BQU0sQ0FBQyxLQUFLLFVBQVUsZUFBZTtJQUNuQyxNQUFNLEdBQUcsR0FBRyxNQUFNLEdBQUcsQ0FBQyxPQUFPLENBQUMsWUFBWSxDQUFDLENBQUM7SUFDNUMsSUFBSSxDQUFDLEtBQUssQ0FBQyxPQUFPLENBQUMsR0FBRyxDQUFDLElBQUksR0FBRyxDQUFDLE1BQU0sS0FBSyxDQUFDLElBQUksT0FBTyxHQUFHLENBQUMsQ0FBQyxDQUFDLEtBQUssUUFBUSxFQUFFO1FBQ3pFLE9BQU8sS0FBSyxDQUFDLE1BQU0sQ0FBQyxDQUFDO0tBQ3RCO0lBQ0QsTUFBTSxJQUFJLEdBQUcsR0FBRyxDQUFDLENBQUMsQ0FBQztTQUNoQixLQUFLLENBQUMsR0FBRyxDQUFDO1NBQ1YsR0FBRyxDQUFDLENBQUMsSUFBSSxFQUFFLEVBQUUsQ0FBQyxNQUFNLENBQUMsUUFBUSxDQUFDLElBQUksRUFBRSxFQUFFLENBQUMsQ0FBQztTQUN4QyxNQUFNLENBQUMsQ0FBQyxHQUFHLEVBQUUsRUFBRSxDQUFDLENBQUMsS0FBSyxDQUFDLEdBQUcsQ0FBQyxJQUFJLEdBQUcsSUFBSSxDQUFDLElBQUksR0FBRyxJQUFJLEdBQUcsQ0FBQyxDQUFDO0lBQzFELElBQUksSUFBSSxDQUFDLE1BQU0sS0FBSyxDQUFDLEVBQUU7UUFDckIsT0FBTyxLQUFLLENBQUMsSUFBSSxDQUFDLENBQUM7S0FDcEI7SUFDRCxPQUFPLElBQUksQ0FBQztBQUNkLENBQUM7QUFDRCwwRUFBMEU7QUFDMUUsNEVBQTRFO0FBQzVFLE1BQU0sVUFBVSxtQkFBbUI7SUFDakMsTUFBTSxLQUFLLEdBQUcsWUFBWSxFQUFFLENBQUM7SUFDN0IsT0FBTyxLQUFLLENBQUMsS0FBSyxDQUFDLENBQUMsSUFBSSxFQUFFLEVBQUUsQ0FBQyxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsQ0FBQyxVQUFVLENBQUMsYUFBYSxDQUFDLENBQUMsQ0FBQztBQUNuRSxDQUFDO0FBQ0QsTUFBTSxVQUFVLGtCQUFrQjtJQUNoQyxNQUFNLEtBQUssR0FBRyxZQUFZLEVBQUUsQ0FBQztJQUM3QixPQUFPLEtBQUssQ0FBQyxJQUFJLENBQUMsQ0FBQyxJQUFJLEVBQUUsRUFBRSxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsQ0FBQyxVQUFVLENBQUMsYUFBYSxDQUFDLENBQUMsQ0FBQztBQUNqRSxDQUFDIn0=