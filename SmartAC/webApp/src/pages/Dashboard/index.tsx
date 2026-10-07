import {Greeting2 as KotlinGreeting} from "webapp-shared";
import {useHealth} from "src/hooks";

function Dashboard() {
    let greeting = new KotlinGreeting();

    let health = useHealth();

    const getVersion = () => {
        if (!health.data) {
            return "";
        }
        const v = health.data.version;
        return `v${v.major}.${v.minor}.${v.patch}`;
    };

    return (
        <div>
            <h1>Dashboard</h1>
            <p>{greeting.greet()}</p>
            <p>Version: ${getVersion()}</p>
        </div>
    );
}

export default Dashboard;