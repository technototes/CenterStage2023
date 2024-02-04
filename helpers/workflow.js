/*
 * This is the 'student workflow' script.
 * See the readme.md file for what it's supposed to do.
 */
import { Error, Menu } from './helpers/menu.js';
import { invoke } from './helpers/invoke.js';
import { hasGithubAccess, anyRobotConnection } from './helpers/connectivity.js';
import { ResumeWork, StartWork } from './helpers/beginWork.js';
import { FinishWork, StopWork } from './helpers/endWork.js';
async function workflow() {
    console.clear();
    await Menu('What do you want to do?', [
        StartWork,
        ResumeWork,
        FinishWork,
        StopWork,
        // ['Configure stuff', configureStuff],
        ['Connect to the control hub', connect],
        ['Disconnect from control hub', disconnect],
        ['Launch the dashboard', launchDash],
        ['Quit', () => Promise.resolve(true)],
    ]);
}
// Nothing in here yet...
async function configureStuff() {
    // This should:
    // Add my silly things to .gitconfig
    console.log('Configuring stuff');
    await hasGithubAccess();
    return Promise.resolve(false);
}
async function connect() {
    if (!anyRobotConnection()) {
        return Error("You don't appear to be connected to a robot.");
    }
    const res = await invoke('yarn connect');
    console.log(res.stdout);
    console.log(res.stderr);
    return false;
}
async function disconnect() {
    const res = await invoke('yarn discall');
    console.log(res.stdout);
    console.log(res.stderr);
    return false;
}
async function launchDash() {
    const res = await invoke('yarn dash');
    console.log(res.stdout);
    console.log(res.stderr);
    return false;
}
workflow()
    .catch((err) => console.error(err))
    .finally(() => {
    // console.log('All Done');
    process.exit(0);
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoid29ya2Zsb3cuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi9oZWxwZXJzLXNyYy93b3JrZmxvdy50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTs7O0dBR0c7QUFDSCxPQUFPLEVBQUUsS0FBSyxFQUFFLElBQUksRUFBRSxNQUFNLG1CQUFtQixDQUFDO0FBQ2hELE9BQU8sRUFBRSxNQUFNLEVBQUUsTUFBTSxxQkFBcUIsQ0FBQztBQUM3QyxPQUFPLEVBQUUsZUFBZSxFQUFFLGtCQUFrQixFQUFFLE1BQU0sMkJBQTJCLENBQUM7QUFDaEYsT0FBTyxFQUFFLFVBQVUsRUFBRSxTQUFTLEVBQUUsTUFBTSx3QkFBd0IsQ0FBQztBQUMvRCxPQUFPLEVBQUUsVUFBVSxFQUFFLFFBQVEsRUFBRSxNQUFNLHNCQUFzQixDQUFDO0FBRTVELEtBQUssVUFBVSxRQUFRO0lBQ3JCLE9BQU8sQ0FBQyxLQUFLLEVBQUUsQ0FBQztJQUNoQixNQUFNLElBQUksQ0FBQyx5QkFBeUIsRUFBRTtRQUNwQyxTQUFTO1FBQ1QsVUFBVTtRQUNWLFVBQVU7UUFDVixRQUFRO1FBQ1IsdUNBQXVDO1FBQ3ZDLENBQUMsNEJBQTRCLEVBQUUsT0FBTyxDQUFDO1FBQ3ZDLENBQUMsNkJBQTZCLEVBQUUsVUFBVSxDQUFDO1FBQzNDLENBQUMsc0JBQXNCLEVBQUUsVUFBVSxDQUFDO1FBQ3BDLENBQUMsTUFBTSxFQUFFLEdBQUcsRUFBRSxDQUFDLE9BQU8sQ0FBQyxPQUFPLENBQUMsSUFBSSxDQUFDLENBQUM7S0FDdEMsQ0FBQyxDQUFDO0FBQ0wsQ0FBQztBQUVELHlCQUF5QjtBQUN6QixLQUFLLFVBQVUsY0FBYztJQUMzQixlQUFlO0lBQ2Ysb0NBQW9DO0lBQ3BDLE9BQU8sQ0FBQyxHQUFHLENBQUMsbUJBQW1CLENBQUMsQ0FBQztJQUNqQyxNQUFNLGVBQWUsRUFBRSxDQUFDO0lBQ3hCLE9BQU8sT0FBTyxDQUFDLE9BQU8sQ0FBQyxLQUFLLENBQUMsQ0FBQztBQUNoQyxDQUFDO0FBRUQsS0FBSyxVQUFVLE9BQU87SUFDcEIsSUFBSSxDQUFDLGtCQUFrQixFQUFFLEVBQUUsQ0FBQztRQUMxQixPQUFPLEtBQUssQ0FBQyw4Q0FBOEMsQ0FBQyxDQUFDO0lBQy9ELENBQUM7SUFDRCxNQUFNLEdBQUcsR0FBRyxNQUFNLE1BQU0sQ0FBQyxjQUFjLENBQUMsQ0FBQztJQUN6QyxPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLEtBQUssQ0FBQztBQUNmLENBQUM7QUFFRCxLQUFLLFVBQVUsVUFBVTtJQUN2QixNQUFNLEdBQUcsR0FBRyxNQUFNLE1BQU0sQ0FBQyxjQUFjLENBQUMsQ0FBQztJQUN6QyxPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLEtBQUssQ0FBQztBQUNmLENBQUM7QUFFRCxLQUFLLFVBQVUsVUFBVTtJQUN2QixNQUFNLEdBQUcsR0FBRyxNQUFNLE1BQU0sQ0FBQyxXQUFXLENBQUMsQ0FBQztJQUN0QyxPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxNQUFNLENBQUMsQ0FBQztJQUN4QixPQUFPLEtBQUssQ0FBQztBQUNmLENBQUM7QUFFRCxRQUFRLEVBQUU7S0FDUCxLQUFLLENBQUMsQ0FBQyxHQUFHLEVBQUUsRUFBRSxDQUFDLE9BQU8sQ0FBQyxLQUFLLENBQUMsR0FBRyxDQUFDLENBQUM7S0FDbEMsT0FBTyxDQUFDLEdBQUcsRUFBRTtJQUNaLDJCQUEyQjtJQUMzQixPQUFPLENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxDQUFDO0FBQ2xCLENBQUMsQ0FBQyxDQUFDIn0=