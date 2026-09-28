import {Greeting2 as KotlinGreeting} from "webapp-shared";

function Dashboard() {
    let greeting = new KotlinGreeting();
    return (
        <div>
            <h1>Dashboard</h1>
            <p>{greeting.greet()}</p>
        </div>
    );
}

export default Dashboard;