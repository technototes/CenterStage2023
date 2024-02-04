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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZW5kV29yay5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uL2hlbHBlcnMtc3JjL2hlbHBlcnMvZW5kV29yay50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLFNBQXlCLE1BQU0sWUFBWSxDQUFDO0FBQ25ELE9BQU8sRUFBRSxLQUFLLEVBQUUsSUFBSSxFQUFZLE1BQU0sV0FBVyxDQUFDO0FBQ2xELE9BQU8sRUFBRSxlQUFlLEVBQUUsbUJBQW1CLEVBQUUsTUFBTSxtQkFBbUIsQ0FBQztBQUN6RSxPQUFPLEVBQUUsTUFBTSxFQUFFLE1BQU0sYUFBYSxDQUFDO0FBQ3JDLE9BQU8sRUFBRSxtQkFBbUIsRUFBRSxjQUFjLEVBQUUsTUFBTSxhQUFhLENBQUM7QUFDbEUsT0FBTyxFQUFFLG9CQUFvQixFQUFFLE1BQU0sYUFBYSxDQUFDO0FBRW5ELE1BQU0sR0FBRyxHQUFHLFNBQVMsRUFBRSxDQUFDO0FBRXhCLE1BQU0sQ0FBQyxNQUFNLFVBQVUsR0FBYTtJQUNsQyxnQ0FBZ0M7SUFDaEMsVUFBVTtDQUNYLENBQUM7QUFDRixNQUFNLENBQUMsTUFBTSxRQUFRLEdBQWE7SUFDaEMsOENBQThDO0lBQzlDLFFBQVE7Q0FDVCxDQUFDO0FBRUYsS0FBSyxVQUFVLFFBQVEsQ0FBQyxLQUFlLEVBQUUsT0FBZTtJQUN0RCxPQUFPLENBQUMsR0FBRyxDQUFDLE1BQU0sR0FBRyxDQUFDLEdBQUcsQ0FBQyxLQUFLLENBQUMsQ0FBQyxNQUFNLENBQUMsT0FBTyxDQUFDLENBQUMsQ0FBQztJQUNsRCxPQUFPLElBQUksQ0FBQztBQUNkLENBQUM7QUFFRCw0QkFBNEI7QUFDNUIsS0FBSyxVQUFVLFlBQVk7SUFDekIsb0JBQW9CO0lBQ3BCLE1BQU0sTUFBTSxHQUFHLE1BQU0sR0FBRyxDQUFDLE1BQU0sRUFBRSxDQUFDO0lBQ2xDLElBQUksQ0FBQyxNQUFNLENBQUMsT0FBTyxFQUFFLEVBQUUsQ0FBQztRQUN0QixPQUFPLEtBQUssQ0FBQyx3REFBd0QsQ0FBQyxDQUFDO0lBQ3pFLENBQUM7SUFFRCxzQkFBc0I7SUFDdEIsSUFBSSxNQUFNLENBQUMsU0FBUyxDQUFDLE1BQU0sS0FBSyxDQUFDLEVBQUUsQ0FBQztRQUNsQyxJQUFJLEtBQUssR0FBRyxLQUFLLENBQUM7UUFDbEIsT0FBTyxDQUFDLEdBQUcsQ0FBQyxjQUFjLENBQUMsQ0FBQztRQUM1QixPQUFPLENBQUMsR0FBRyxDQUFDLE1BQU0sQ0FBQyxTQUFTLENBQUMsQ0FBQztRQUM5QixNQUFNLElBQUksQ0FBQyxpREFBaUQsRUFBRTtZQUM1RDtnQkFDRSwwREFBMEQ7Z0JBQzFELEdBQUcsRUFBRSxDQUFDLE9BQU8sQ0FBQyxPQUFPLENBQUMsSUFBSSxDQUFDO2FBQzVCO1lBQ0Q7Z0JBQ0Usb0NBQW9DO2dCQUNwQyxHQUFHLEVBQUUsQ0FBQyxRQUFRLENBQUMsTUFBTSxDQUFDLFNBQVMsRUFBRSwwQkFBMEIsQ0FBQzthQUM3RDtZQUNEO2dCQUNFLHNCQUFzQjtnQkFDdEIsR0FBRyxFQUFFO29CQUNILEtBQUssR0FBRyxJQUFJLENBQUM7b0JBQ2IsT0FBTyxPQUFPLENBQUMsT0FBTyxDQUFDLElBQUksQ0FBQyxDQUFDO2dCQUMvQixDQUFDO2FBQ0Y7U0FDRixDQUFDLENBQUM7UUFDSCxJQUFJLEtBQUssRUFBRSxDQUFDO1lBQ1YsT0FBTyxLQUFLLENBQUM7UUFDZixDQUFDO0lBQ0gsQ0FBQztJQUVELDJCQUEyQjtJQUMzQixJQUFJLENBQUMsQ0FBQyxNQUFNLGVBQWUsRUFBRSxDQUFDLEVBQUUsQ0FBQztRQUMvQixJQUFJLG1CQUFtQixFQUFFLEVBQUUsQ0FBQztZQUMxQixPQUFPLEtBQUssQ0FDVixpRkFBaUYsQ0FDbEYsQ0FBQztRQUNKLENBQUM7YUFBTSxDQUFDO1lBQ04sT0FBTyxLQUFLLENBQ1YsbUVBQW1FLENBQ3BFLENBQUM7UUFDSixDQUFDO0lBQ0gsQ0FBQztJQUVELDBCQUEwQjtJQUMxQixNQUFNLE1BQU0sQ0FBQyxhQUFhLENBQUMsQ0FBQztJQUM1QixNQUFNLE9BQU8sR0FBRyxNQUFNLEdBQUcsQ0FBQyxNQUFNLEVBQUUsQ0FBQztJQUNuQyxJQUFJLENBQUMsT0FBTyxDQUFDLE9BQU8sRUFBRSxFQUFFLENBQUM7UUFDdkIsT0FBTyxDQUFDLEdBQUcsQ0FBQyxxREFBcUQsQ0FBQyxDQUFDO1FBQ25FLE1BQU0sUUFBUSxDQUFDLE9BQU8sQ0FBQyxRQUFRLEVBQUUsc0JBQXNCLENBQUMsQ0FBQztJQUMzRCxDQUFDO0lBRUQsTUFBTSxrQkFBa0IsR0FBRyxNQUFNLGNBQWMsRUFBRSxDQUFDO0lBQ2xELElBQUksa0JBQWtCLEtBQUssS0FBSyxFQUFFLENBQUM7UUFDakMsT0FBTyxDQUFDLEdBQUcsQ0FBQyxzQ0FBc0MsQ0FBQyxDQUFDO1FBQ3BELE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUNELG9DQUFvQztJQUNwQyxPQUFPLE1BQU0sR0FBRyxDQUFDLElBQUksQ0FBQyxDQUFDLElBQUksRUFBRSxRQUFRLEVBQUUsa0JBQWtCLENBQUMsQ0FBQyxDQUFDO0FBQzlELENBQUM7QUFFRCxLQUFLLFVBQVUsUUFBUTtJQUNyQixlQUFlO0lBQ2YsZ0NBQWdDO0lBQ2hDLGdDQUFnQztJQUNoQyx1Q0FBdUM7SUFDdkMseURBQXlEO0lBQ3pELDRCQUE0QjtJQUM1QixtQkFBbUI7SUFDbkIsTUFBTSxnQkFBZ0IsR0FBRyxNQUFNLFlBQVksRUFBRSxDQUFDO0lBRTlDLElBQUksZ0JBQWdCLEtBQUssS0FBSyxFQUFFLENBQUM7UUFDL0IsT0FBTyxLQUFLLENBQUM7SUFDZixDQUFDO0lBRUQsaUJBQWlCO0lBQ2pCLE9BQU8sQ0FBQyxHQUFHLENBQUMsTUFBTSxHQUFHLENBQUMsUUFBUSxDQUFDLG1CQUFtQixDQUFDLENBQUMsQ0FBQztJQUNyRCxNQUFNLEdBQUcsR0FBRyxNQUFNLEdBQUcsQ0FBQyxNQUFNLEVBQUUsQ0FBQztJQUMvQixJQUFJLEdBQUcsQ0FBQyxPQUFPLEtBQUssbUJBQW1CLEVBQUUsQ0FBQztRQUN4QyxPQUFPLEtBQUssQ0FBQywyQkFBMkIsbUJBQW1CLFVBQVUsQ0FBQyxDQUFDO0lBQ3pFLENBQUM7SUFDRCxPQUFPLEtBQUssQ0FBQztBQUNmLENBQUM7QUFFRCxLQUFLLFVBQVUsVUFBVTtJQUN2QixlQUFlO0lBQ2YsZ0NBQWdDO0lBQ2hDLGdDQUFnQztJQUNoQyx1Q0FBdUM7SUFDdkMseURBQXlEO0lBQ3pELDRCQUE0QjtJQUM1QixrQkFBa0I7SUFDbEIsK0NBQStDO0lBRS9DLE1BQU0sZ0JBQWdCLEdBQUcsTUFBTSxZQUFZLEVBQUUsQ0FBQztJQUU5QyxJQUFJLGdCQUFnQixLQUFLLEtBQUssRUFBRSxDQUFDO1FBQy9CLE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUNELE1BQU0sT0FBTyxHQUFlLGdCQUFnQixDQUFDO0lBQzdDLE1BQU0sTUFBTSxHQUFHLE1BQU0sY0FBYyxFQUFFLENBQUM7SUFDdEMsSUFBSSxNQUFNLEtBQUssS0FBSyxJQUFJLE9BQU8sT0FBTyxDQUFDLElBQUksS0FBSyxRQUFRLEVBQUUsQ0FBQztRQUN6RCxPQUFPLENBQUMsR0FBRyxDQUFDLGlDQUFpQyxDQUFDLENBQUM7UUFDL0MsT0FBTyxJQUFJLENBQUM7SUFDZCxDQUFDO0lBQ0QsTUFBTSxHQUFHLEdBQUcsR0FBRyxvQkFBb0IsQ0FDakMsT0FBTyxDQUFDLElBQUksQ0FDYixZQUFZLG1CQUFtQixNQUFNLE1BQU0sRUFBRSxDQUFDO0lBQy9DLFFBQVEsT0FBTyxDQUFDLFFBQVEsRUFBRSxDQUFDO1FBQ3pCLEtBQUssT0FBTztZQUNWLE1BQU0sTUFBTSxDQUFDLGFBQWEsR0FBRyxHQUFHLENBQUMsQ0FBQztZQUNsQyxNQUFNO1FBQ1IsS0FBSyxRQUFRO1lBQ1gsTUFBTSxNQUFNLENBQUMsT0FBTyxHQUFHLEdBQUcsQ0FBQyxDQUFDO1lBQzVCLE1BQU07UUFDUixLQUFLLE9BQU87WUFDVixNQUFNLE1BQU0sQ0FBQyxXQUFXLEdBQUcsR0FBRyxDQUFDLENBQUM7WUFDaEMsTUFBTTtJQUNWLENBQUM7SUFDRCxPQUFPLEtBQUssQ0FBQztBQUNmLENBQUMifQ==