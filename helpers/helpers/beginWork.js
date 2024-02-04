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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmVnaW5Xb3JrLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vaGVscGVycy1zcmMvaGVscGVycy9iZWdpbldvcmsudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxTQUFTLE1BQU0sWUFBWSxDQUFDO0FBQ25DLE9BQU8sRUFBRSxLQUFLLEVBQVksS0FBSyxFQUFFLE1BQU0sV0FBVyxDQUFDO0FBQ25ELE9BQU8sRUFBRSxlQUFlLEVBQUUsbUJBQW1CLEVBQUUsTUFBTSxtQkFBbUIsQ0FBQztBQUN6RSxPQUFPLEVBQ0wsbUJBQW1CLEVBQ25CLGFBQWEsRUFDYixvQkFBb0IsR0FDckIsTUFBTSxhQUFhLENBQUM7QUFFckIsTUFBTSxHQUFHLEdBQUcsU0FBUyxFQUFFLENBQUM7QUFFeEIsTUFBTSxDQUFDLE1BQU0sU0FBUyxHQUFhLENBQUMsNEJBQTRCLEVBQUUsU0FBUyxDQUFDLENBQUM7QUFDN0UsTUFBTSxDQUFDLE1BQU0sVUFBVSxHQUFhO0lBQ2xDLCtCQUErQjtJQUMvQixVQUFVO0NBQ1gsQ0FBQztBQUVGLDRCQUE0QjtBQUM1QixLQUFLLFVBQVUsVUFBVTtJQUN2QixvQkFBb0I7SUFDcEIsTUFBTSxNQUFNLEdBQUcsTUFBTSxHQUFHLENBQUMsTUFBTSxFQUFFLENBQUM7SUFDbEMsSUFBSSxDQUFDLE1BQU0sQ0FBQyxPQUFPLEVBQUUsRUFBRTtRQUNyQixPQUFPLEtBQUssQ0FBQyx3REFBd0QsQ0FBQyxDQUFDO0tBQ3hFO0lBRUQsc0JBQXNCO0lBQ3RCLElBQUksQ0FBQyxDQUFDLE1BQU0sZUFBZSxFQUFFLENBQUMsRUFBRTtRQUM5QixJQUFJLG1CQUFtQixFQUFFLEVBQUU7WUFDekIsT0FBTyxLQUFLLENBQ1YsaUZBQWlGLENBQ2xGLENBQUM7U0FDSDthQUFNO1lBQ0wsT0FBTyxLQUFLLENBQ1YsbUVBQW1FLENBQ3BFLENBQUM7U0FDSDtLQUNGO0lBRUQsaUJBQWlCO0lBQ2pCLE9BQU8sQ0FBQyxHQUFHLENBQUMsTUFBTSxHQUFHLENBQUMsUUFBUSxDQUFDLG1CQUFtQixDQUFDLENBQUMsQ0FBQztJQUNyRCxNQUFNLEdBQUcsR0FBRyxNQUFNLEdBQUcsQ0FBQyxNQUFNLEVBQUUsQ0FBQztJQUMvQixJQUFJLEdBQUcsQ0FBQyxPQUFPLEtBQUssbUJBQW1CLEVBQUU7UUFDdkMsT0FBTyxLQUFLLENBQUMsMkJBQTJCLG1CQUFtQixVQUFVLENBQUMsQ0FBQztLQUN4RTtJQUNELG1CQUFtQjtJQUNuQixxQkFBcUIsQ0FBQyxNQUFNLEdBQUcsQ0FBQyxJQUFJLEVBQUUsQ0FBQztJQUN2Qyx3QkFBd0I7SUFDeEIsT0FBTyxJQUFJLENBQUM7QUFDZCxDQUFDO0FBRUQsS0FBSyxVQUFVLFNBQVM7SUFDdEIsZUFBZTtJQUNmLDZCQUE2QjtJQUM3QixnQ0FBZ0M7SUFDaEMsdUNBQXVDO0lBQ3ZDLG1CQUFtQjtJQUNuQixtQkFBbUI7SUFDbkIsMkJBQTJCO0lBQzNCLHFDQUFxQztJQUNyQywyQkFBMkI7SUFFM0IsSUFBSSxDQUFDLE1BQU0sVUFBVSxFQUFFLENBQUMsS0FBSyxLQUFLLEVBQUU7UUFDbEMsT0FBTyxLQUFLLENBQUM7S0FDZDtJQUNELGtDQUFrQztJQUNsQyxNQUFNLFVBQVUsR0FBRyxNQUFNLGFBQWEsRUFBRSxDQUFDO0lBQ3pDLElBQUksT0FBTyxVQUFVLEtBQUssUUFBUSxFQUFFO1FBQ2xDLE9BQU8sS0FBSyxDQUFDO0tBQ2Q7SUFDRCwwQkFBMEI7SUFDMUIsbUJBQW1CLENBQUMsTUFBTSxHQUFHLENBQUMsUUFBUSxDQUFDLENBQUMsSUFBSSxFQUFFLFVBQVUsQ0FBQyxDQUFDLENBQUM7SUFDM0Qsc0JBQXNCO0lBQ3RCLE9BQU8sQ0FBQyxHQUFHLENBQ1Qsa0VBQWtFLENBQ25FLENBQUM7SUFDRixNQUFNLEtBQUssQ0FBQyxJQUFJLENBQUMsQ0FBQztJQUNsQiwyQ0FBMkM7SUFDM0MsT0FBTyxLQUFLLENBQUM7QUFDZixDQUFDO0FBRUQsS0FBSyxVQUFVLFVBQVU7SUFDdkIsZUFBZTtJQUVmLDZCQUE2QjtJQUM3QixnQ0FBZ0M7SUFDaEMsdUNBQXVDO0lBQ3ZDLG1CQUFtQjtJQUNuQixtQkFBbUI7SUFDbkIsMkJBQTJCO0lBRTNCLDBCQUEwQjtJQUMxQixrQ0FBa0M7SUFDbEMscUNBQXFDO0lBQ3JDLGtDQUFrQztJQUNsQywyQkFBMkI7SUFFM0IsSUFBSSxDQUFDLE1BQU0sVUFBVSxFQUFFLENBQUMsS0FBSyxLQUFLLEVBQUU7UUFDbEMsT0FBTyxLQUFLLENBQUM7S0FDZDtJQUVELHdDQUF3QztJQUN4QyxNQUFNLG9CQUFvQixFQUFFLENBQUM7SUFDN0IsT0FBTyxDQUFDLEdBQUcsQ0FBQyw0Q0FBNEMsQ0FBQyxDQUFDO0lBQzFELE1BQU0sS0FBSyxDQUFDLElBQUksQ0FBQyxDQUFDO0lBQ2xCLDJDQUEyQztJQUMzQyxPQUFPLEtBQUssQ0FBQztBQUNmLENBQUMifQ==