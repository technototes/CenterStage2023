import readline from 'readline';
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});
export const Ask = (query) => new Promise((resolve) => rl.question(query, resolve));
// Display a menu with a header, and run the function for the menu item selected
export async function Menu(header, menu) {
    let done = false;
    while (!done) {
        const bar = '*'.repeat(header.length + 4);
        console.log();
        console.log(bar);
        console.log('*', header, '*');
        console.log(bar);
        console.log();
        menu.forEach((val, i) => console.log(`${i + 1}: ${val[0]}`));
        console.log();
        const res = await Ask('Please select an option: ');
        const cleaned = res.trim();
        const opt = Number.parseInt(cleaned, 10);
        if (isNaN(opt) || opt < 1 || opt > menu.length) {
            // Let's check for unique first letters, just for fun...
            if (isNaN(opt) && cleaned.length === 1) {
                const itemsMatched = menu.filter((mnuItem) => mnuItem[0][0].toLowerCase() === cleaned.toLowerCase());
                // If we found *only one* menu item with that first character,
                // proceed with that selection
                if (itemsMatched.length === 1) {
                    done = await itemsMatched[0][1]();
                    continue;
                }
            }
            console.error(`Please enter a number from 1 to ${menu.length}`);
        }
        else {
            done = await menu[opt - 1][1]();
        }
    }
}
export function Sleep(ms) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}
export async function Error(message) {
    console.error('>>>> ');
    console.error('>>>>', message);
    console.error('>>>> ');
    await Sleep(3000);
    return false;
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoibWVudS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uL2hlbHBlcnMtc3JjL2hlbHBlcnMvbWVudS50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLFFBQVEsTUFBTSxVQUFVLENBQUM7QUFFaEMsTUFBTSxFQUFFLEdBQUcsUUFBUSxDQUFDLGVBQWUsQ0FBQztJQUNsQyxLQUFLLEVBQUUsT0FBTyxDQUFDLEtBQUs7SUFDcEIsTUFBTSxFQUFFLE9BQU8sQ0FBQyxNQUFNO0NBQ3ZCLENBQUMsQ0FBQztBQUVILE1BQU0sQ0FBQyxNQUFNLEdBQUcsR0FBRyxDQUFDLEtBQWEsRUFBbUIsRUFBRSxDQUNwRCxJQUFJLE9BQU8sQ0FBQyxDQUFDLE9BQU8sRUFBRSxFQUFFLENBQUMsRUFBRSxDQUFDLFFBQVEsQ0FBQyxLQUFLLEVBQUUsT0FBTyxDQUFDLENBQUMsQ0FBQztBQUl4RCxnRkFBZ0Y7QUFDaEYsTUFBTSxDQUFDLEtBQUssVUFBVSxJQUFJLENBQUMsTUFBYyxFQUFFLElBQWdCO0lBQ3pELElBQUksSUFBSSxHQUFHLEtBQUssQ0FBQztJQUNqQixPQUFPLENBQUMsSUFBSSxFQUFFO1FBQ1osTUFBTSxHQUFHLEdBQUcsR0FBRyxDQUFDLE1BQU0sQ0FBQyxNQUFNLENBQUMsTUFBTSxHQUFHLENBQUMsQ0FBQyxDQUFDO1FBQzFDLE9BQU8sQ0FBQyxHQUFHLEVBQUUsQ0FBQztRQUNkLE9BQU8sQ0FBQyxHQUFHLENBQUMsR0FBRyxDQUFDLENBQUM7UUFDakIsT0FBTyxDQUFDLEdBQUcsQ0FBQyxHQUFHLEVBQUUsTUFBTSxFQUFFLEdBQUcsQ0FBQyxDQUFDO1FBQzlCLE9BQU8sQ0FBQyxHQUFHLENBQUMsR0FBRyxDQUFDLENBQUM7UUFDakIsT0FBTyxDQUFDLEdBQUcsRUFBRSxDQUFDO1FBQ2QsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDLEdBQXFDLEVBQUUsQ0FBUyxFQUFFLEVBQUUsQ0FDaEUsT0FBTyxDQUFDLEdBQUcsQ0FBQyxHQUFHLENBQUMsR0FBRyxDQUFDLEtBQUssR0FBRyxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FDbkMsQ0FBQztRQUNGLE9BQU8sQ0FBQyxHQUFHLEVBQUUsQ0FBQztRQUNkLE1BQU0sR0FBRyxHQUFHLE1BQU0sR0FBRyxDQUFDLDJCQUEyQixDQUFDLENBQUM7UUFDbkQsTUFBTSxPQUFPLEdBQUcsR0FBRyxDQUFDLElBQUksRUFBRSxDQUFDO1FBQzNCLE1BQU0sR0FBRyxHQUFHLE1BQU0sQ0FBQyxRQUFRLENBQUMsT0FBTyxFQUFFLEVBQUUsQ0FBQyxDQUFDO1FBQ3pDLElBQUksS0FBSyxDQUFDLEdBQUcsQ0FBQyxJQUFJLEdBQUcsR0FBRyxDQUFDLElBQUksR0FBRyxHQUFHLElBQUksQ0FBQyxNQUFNLEVBQUU7WUFDOUMsd0RBQXdEO1lBQ3hELElBQUksS0FBSyxDQUFDLEdBQUcsQ0FBQyxJQUFJLE9BQU8sQ0FBQyxNQUFNLEtBQUssQ0FBQyxFQUFFO2dCQUN0QyxNQUFNLFlBQVksR0FBRyxJQUFJLENBQUMsTUFBTSxDQUM5QixDQUFDLE9BQWlCLEVBQUUsRUFBRSxDQUNwQixPQUFPLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUMsV0FBVyxFQUFFLEtBQUssT0FBTyxDQUFDLFdBQVcsRUFBRSxDQUN4RCxDQUFDO2dCQUNGLDhEQUE4RDtnQkFDOUQsOEJBQThCO2dCQUM5QixJQUFJLFlBQVksQ0FBQyxNQUFNLEtBQUssQ0FBQyxFQUFFO29CQUM3QixJQUFJLEdBQUcsTUFBTSxZQUFZLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQztvQkFDbEMsU0FBUztpQkFDVjthQUNGO1lBQ0QsT0FBTyxDQUFDLEtBQUssQ0FBQyxtQ0FBbUMsSUFBSSxDQUFDLE1BQU0sRUFBRSxDQUFDLENBQUM7U0FDakU7YUFBTTtZQUNMLElBQUksR0FBRyxNQUFNLElBQUksQ0FBQyxHQUFHLEdBQUcsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQztTQUNqQztLQUNGO0FBQ0gsQ0FBQztBQUVELE1BQU0sVUFBVSxLQUFLLENBQUMsRUFBVTtJQUM5QixPQUFPLElBQUksT0FBTyxDQUFDLENBQUMsT0FBTyxFQUFFLEVBQUU7UUFDN0IsVUFBVSxDQUFDLE9BQU8sRUFBRSxFQUFFLENBQUMsQ0FBQztJQUMxQixDQUFDLENBQUMsQ0FBQztBQUNMLENBQUM7QUFFRCxNQUFNLENBQUMsS0FBSyxVQUFVLEtBQUssQ0FBQyxPQUFlO0lBQ3pDLE9BQU8sQ0FBQyxLQUFLLENBQUMsT0FBTyxDQUFDLENBQUM7SUFDdkIsT0FBTyxDQUFDLEtBQUssQ0FBQyxNQUFNLEVBQUUsT0FBTyxDQUFDLENBQUM7SUFDL0IsT0FBTyxDQUFDLEtBQUssQ0FBQyxPQUFPLENBQUMsQ0FBQztJQUN2QixNQUFNLEtBQUssQ0FBQyxJQUFJLENBQUMsQ0FBQztJQUNsQixPQUFPLEtBQUssQ0FBQztBQUNmLENBQUMifQ==