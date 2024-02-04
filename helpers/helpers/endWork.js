import simpleGit from 'simple-git';
import { Error, Menu } from './menu.js';
import { hasGithubAccess, onlyRobotConnection } from './connectivity.js';
import { invoke } from './invoke.js';
import { DEFAULT_BRANCH_NAME, ReadBranchName } from './branch.js';
import { GetGitHubUrlFromRepo } from './github.js';
const git = simpleGit();
export const FinishWork = [
    'Finish work: ready for merging',
    finishWork,
];
export const StopWork = [
    'Stop for the day (not yet ready for merging)',
    stopWork,
];
async function addFiles(files, message) {
    console.log(await git.add(files).commit(message));
    return true;
}
// Helper for stop & finish:
async function completeWork() {
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
            return Error("It looks like you're connected to the robot. Please fix that before continuing.");
        }
        else {
            return Error('Unable to communicate with GitHub: Check your internet connection');
        }
    }
    // Run the code formatting
    await invoke('yarn format');
    const fmtStat = await git.status();
    if (!fmtStat.isClean()) {
        console.log('Some files were re-formatted. Commiting the change.');
        await addFiles(fmtStat.modified, 'Auto-formatted files');
    }
    const curBranchNameOrErr = await ReadBranchName();
    if (curBranchNameOrErr === false) {
        console.log("Couldn't get the curent branch name.");
        return false;
    }
    // Push the code (Wire it  properly)
    return await git.push(['-u', 'origin', curBranchNameOrErr]);
}
async function stopWork() {
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
async function finishWork() {
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
    const pullRes = failureOrPullRes;
    const branch = await ReadBranchName();
    if (branch === false || typeof pullRes.repo !== 'string') {
        console.log('Some weird error: Ask for help.');
        return true;
    }
    const url = `${GetGitHubUrlFromRepo(pullRes.repo)}/compare/${DEFAULT_BRANCH_NAME}...${branch}`;
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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZW5kV29yay5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uL2hlbHBlcnMtc3JjL2hlbHBlcnMvZW5kV29yay50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLFNBQXlCLE1BQU0sWUFBWSxDQUFDO0FBQ25ELE9BQU8sRUFBRSxLQUFLLEVBQUUsSUFBSSxFQUFZLE1BQU0sV0FBVyxDQUFDO0FBQ2xELE9BQU8sRUFBRSxlQUFlLEVBQUUsbUJBQW1CLEVBQUUsTUFBTSxtQkFBbUIsQ0FBQztBQUN6RSxPQUFPLEVBQUUsTUFBTSxFQUFFLE1BQU0sYUFBYSxDQUFDO0FBQ3JDLE9BQU8sRUFBRSxtQkFBbUIsRUFBRSxjQUFjLEVBQUUsTUFBTSxhQUFhLENBQUM7QUFDbEUsT0FBTyxFQUFFLG9CQUFvQixFQUFFLE1BQU0sYUFBYSxDQUFDO0FBRW5ELE1BQU0sR0FBRyxHQUFHLFNBQVMsRUFBRSxDQUFDO0FBRXhCLE1BQU0sQ0FBQyxNQUFNLFVBQVUsR0FBYTtJQUNsQyxnQ0FBZ0M7SUFDaEMsVUFBVTtDQUNYLENBQUM7QUFDRixNQUFNLENBQUMsTUFBTSxRQUFRLEdBQWE7SUFDaEMsOENBQThDO0lBQzlDLFFBQVE7Q0FDVCxDQUFDO0FBRUYsS0FBSyxVQUFVLFFBQVEsQ0FBQyxLQUFlLEVBQUUsT0FBZTtJQUN0RCxPQUFPLENBQUMsR0FBRyxDQUFDLE1BQU0sR0FBRyxDQUFDLEdBQUcsQ0FBQyxLQUFLLENBQUMsQ0FBQyxNQUFNLENBQUMsT0FBTyxDQUFDLENBQUMsQ0FBQztJQUNsRCxPQUFPLElBQUksQ0FBQztBQUNkLENBQUM7QUFFRCw0QkFBNEI7QUFDNUIsS0FBSyxVQUFVLFlBQVk7SUFDekIsb0JBQW9CO0lBQ3BCLE1BQU0sTUFBTSxHQUFHLE1BQU0sR0FBRyxDQUFDLE1BQU0sRUFBRSxDQUFDO0lBQ2xDLElBQUksQ0FBQyxNQUFNLENBQUMsT0FBTyxFQUFFLEVBQUU7UUFDckIsT0FBTyxLQUFLLENBQUMsd0RBQXdELENBQUMs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