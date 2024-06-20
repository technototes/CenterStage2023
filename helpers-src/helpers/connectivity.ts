import { promises as dns } from 'dns';
import { networkInterfaces } from 'os';
import { Error } from './menu.js';

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
export async function hasGithubAccess(): Promise<boolean> {
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
export function onlyRobotConnection(): boolean {
  const addrs = getAddresses();
  return addrs.every((addr) => !addr[1].startsWith('192.168.43.'));
}
export function anyRobotConnection(): boolean {
  const addrs = getAddresses();
  return addrs.some((addr) => addr[1].startsWith('192.168.43.'));
}
