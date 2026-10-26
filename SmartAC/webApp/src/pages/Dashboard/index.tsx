import {useHealth} from "src/hooks";

function Dashboard() {
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
            <p>Version: {getVersion()}</p>
        </div>
    );
}

export default Dashboard;