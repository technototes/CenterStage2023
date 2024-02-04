import simpleGit from 'simple-git';
import { Error, Sleep } from './menu.js';
import { hasGithubAccess, onlyRobotConnection } from './connectivity.js';
import { DEFAULT_BRANCH_NAME, GetBranchName, PickBranchToContinue, } from './branch.js';
const git = simpleGit();
export const StartWork = ['Begin new work for the day', startWork];
export const ResumeWork = [
    'Resume work from previous day',
    resumeWork,
];
// Helper for start & resumt
async function getStarted() {
    // Repo dirty check:
    const status = await git.status();
    if (!status.isClean()) {
        return Error("You appear to have some work that isn't yet committed.");
    }
    // GitHub access check
    if (!(await hasGithubAccess())) {
        if (onlyRobotConnection()) {
            return Error("It looks like you're connected to the robot. Please fix that before continuing.");
        }
        else {
            return Error('Unable to communicate with GitHub: Check your internet connection');
        }
    }
    // Check out main
    console.log(await git.checkout(DEFAULT_BRANCH_NAME));
    const cur = await git.branch();
    if (cur.current !== DEFAULT_BRANCH_NAME) {
        return Error(`Unable to check out the ${DEFAULT_BRANCH_NAME} branch.`);
    }
    // Pull from github
    /* const pullRes = */ await git.pull();
    // console.log(pullRes);
    return true;
}
async function startWork() {
    // This should:
    // --- Begin getStarted stuff
    // - Ensure the repo isn't dirty
    // - Make sure we've got network access
    // - Check out main
    // - Pull from main
    // --- End getStarted stuff
    // - Create a new branch for the work
    // - Open up Android Studio
    if ((await getStarted()) === false) {
        return false;
    }
    // Get the name for the new branch
    const branchName = await GetBranchName();
    if (typeof branchName !== 'string') {
        return false;
    }
    // Let's create the branch
    /* const coRes = */ await git.checkout(['-b', branchName]);
    // console.log(coRes);
    console.log("You're ready to code! Come back to this window when you're done.");
    await Sleep(3000);
    // Maybe open android studio automatically?
    return false;
}
async function resumeWork() {
    // This should:
    // --- Begin getStarted stuff
    // - Ensure the repo isn't dirty
    // - Make sure we've got network access
    // - Check out main
    // - Pull from main
    // --- End getStarted stuff
    // - Pull all branch names
    // - Ask which branch to check out
    // - Check out the branch to continue
    // - Ensure it's up-to-date (pull)
    // - Open up Android Studio
    if ((await getStarted()) === false) {
        return false;
    }
    // Continue from "pull all branch names"
    await PickBranchToContinue();
    console.log("Come back to this window when you're done.");
    await Sleep(3000);
    // Maybe open android studio automatically?
    return false;
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmVnaW5Xb3JrLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vaGVscGVycy1zcmMvaGVscGVycy9iZWdpbldvcmsudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxTQUFTLE1BQU0sWUFBWSxDQUFDO0FBQ25DLE9BQU8sRUFBRSxLQUFLLEVBQVksS0FBSyxFQUFFLE1BQU0sV0FBVyxDQUFDO0FBQ25ELE9BQU8sRUFBRSxlQUFlLEVBQUUsbUJBQW1CLEVBQUUsTUFBTSxtQkFBbUIsQ0FBQztBQUN6RSxPQUFPLEVBQ0wsbUJBQW1CLEVBQ25CLGFBQWEsRUFDYixvQkFBb0IsR0FDckIsTUFBTSxhQUFhLENBQUM7QUFFckIsTUFBTSxHQUFHLEdBQUcsU0FBUyxFQUFFLENBQUM7QUFFeEIsTUFBTSxDQUFDLE1BQU0sU0FBUyxHQUFhLENBQUMsNEJBQTRCLEVBQUUsU0FBUyxDQUFDLENBQUM7QUFDN0UsTUFBTSxDQUFDLE1BQU0sVUFBVSxHQUFhO0lBQ2xDLCtCQUErQjtJQUMvQixVQUFVO0NBQ1gsQ0FBQztBQUVGLDRCQUE0QjtBQUM1QixLQUFLLFVBQVUsVUFBVTtJQUN2QixvQkFBb0I7SUFDcEIsTUFBTSxNQUFNLEdBQUcsTUFBTSxHQUFHLENBQUMsTUFBTSxFQUFFLENBQUM7SUFDbEMsSUFBSSxDQUFDLE1BQU0sQ0FBQyxPQUFPLEVBQUUsRUFBRSxDQUFDO1FBQ3RCLE9BQU8sS0FBSyxDQUFDLHdEQUF3RCxDQUFDLENBQUM7SUFDekUsQ0FBQztJQUVELHNCQUFzQjtJQUN0QixJQUFJLENBQUMsQ0FBQyxNQUFNLGVBQWUsRUFBRSxDQUFDLEVBQUUsQ0FBQztRQUMvQixJQUFJLG1CQUFtQixFQUFFLEVBQUUsQ0FBQztZQUMxQixPQUFPLEtBQUssQ0FDVixpRkFBaUYsQ0FDbEYsQ0FBQztRQUNKLENBQUM7YUFBTSxDQUFDO1lBQ04sT0FBTyxLQUFLLENBQ1YsbUVBQW1FLENBQ3BFLENBQUM7UUFDSixDQUFDO0lBQ0gsQ0FBQztJQUVELGlCQUFpQjtJQUNqQixPQUFPLENBQUMsR0FBRyxDQUFDLE1BQU0sR0FBRyxDQUFDLFFBQVEsQ0FBQyxtQkFBbUIsQ0FBQyxDQUFDLENBQUM7SUFDckQsTUFBTSxHQUFHLEdBQUcsTUFBTSxHQUFHLENBQUMsTUFBTSxFQUFFLENBQUM7SUFDL0IsSUFBSSxHQUFHLENBQUMsT0FBTyxLQUFLLG1CQUFtQixFQUFFLENBQUM7UUFDeEMsT0FBTyxLQUFLLENBQUMsMkJBQTJCLG1CQUFtQixVQUFVLENBQUMsQ0FBQztJQUN6RSxDQUFDO0lBQ0QsbUJBQW1CO0lBQ25CLHFCQUFxQixDQUFDLE1BQU0sR0FBRyxDQUFDLElBQUksRUFBRSxDQUFDO0lBQ3ZDLHdCQUF3QjtJQUN4QixPQUFPLElBQUksQ0FBQztBQUNkLENBQUM7QUFFRCxLQUFLLFVBQVUsU0FBUztJQUN0QixlQUFlO0lBQ2YsNkJBQTZCO0lBQzdCLGdDQUFnQztJQUNoQyx1Q0FBdUM7SUFDdkMsbUJBQW1CO0lBQ25CLG1CQUFtQjtJQUNuQiwyQkFBMkI7SUFDM0IscUNBQXFDO0lBQ3JDLDJCQUEyQjtJQUUzQixJQUFJLENBQUMsTUFBTSxVQUFVLEVBQUUsQ0FBQyxLQUFLLEtBQUssRUFBRSxDQUFDO1FBQ25DLE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUNELGtDQUFrQztJQUNsQyxNQUFNLFVBQVUsR0FBRyxNQUFNLGFBQWEsRUFBRSxDQUFDO0lBQ3pDLElBQUksT0FBTyxVQUFVLEtBQUssUUFBUSxFQUFFLENBQUM7UUFDbkMsT0FBTyxLQUFLLENBQUM7SUFDZixDQUFDO0lBQ0QsMEJBQTBCO0lBQzFCLG1CQUFtQixDQUFDLE1BQU0sR0FBRyxDQUFDLFFBQVEsQ0FBQyxDQUFDLElBQUksRUFBRSxVQUFVLENBQUMsQ0FBQyxDQUFDO0lBQzNELHNCQUFzQjtJQUN0QixPQUFPLENBQUMsR0FBRyxDQUNULGtFQUFrRSxDQUNuRSxDQUFDO0lBQ0YsTUFBTSxLQUFLLENBQUMsSUFBSSxDQUFDLENBQUM7SUFDbEIsMkNBQTJDO0lBQzNDLE9BQU8sS0FBSyxDQUFDO0FBQ2YsQ0FBQztBQUVELEtBQUssVUFBVSxVQUFVO0lBQ3ZCLGVBQWU7SUFFZiw2QkFBNkI7SUFDN0IsZ0NBQWdDO0lBQ2hDLHVDQUF1QztJQUN2QyxtQkFBbUI7SUFDbkIsbUJBQW1CO0lBQ25CLDJCQUEyQjtJQUUzQiwwQkFBMEI7SUFDMUIsa0NBQWtDO0lBQ2xDLHFDQUFxQztJQUNyQyxrQ0FBa0M7SUFDbEMsMkJBQTJCO0lBRTNCLElBQUksQ0FBQyxNQUFNLFVBQVUsRUFBRSxDQUFDLEtBQUssS0FBSyxFQUFFLENBQUM7UUFDbkMsT0FBTyxLQUFLLENBQUM7SUFDZixDQUFDO0lBRUQsd0NBQXdDO0lBQ3hDLE1BQU0sb0JBQW9CLEVBQUUsQ0FBQztJQUM3QixPQUFPLENBQUMsR0FBRyxDQUFDLDRDQUE0QyxDQUFDLENBQUM7SUFDMUQsTUFBTSxLQUFLLENBQUMsSUFBSSxDQUFDLENBQUM7SUFDbEIsMkNBQTJDO0lBQzNDLE9BQU8sS0FBSyxDQUFDO0FBQ2YsQ0FBQyJ9