import "./App.css";
import {Routes, Route, useNavigate} from "react-router-dom";
import {CssBaseline, Tab, Tabs, Toolbar} from "@mui/material";
import Devices from "./Pages/Devices/Devices.tsx";
import Users from "./Pages/Users/Users.tsx";

function App() {

    let navigate = useNavigate();

    return (
        <>
            <CssBaseline/>
            <main className="main-content">
                <Toolbar className="navbar">
                    <Tabs>
                        <Tab label="Users" onClick={() => navigate("/users")}/>
                        <Tab label="Devices" onClick={() => navigate("/devices")}/>
                    </Tabs>
                </Toolbar>
                <Routes>
                    <Route path="/users" element={<Users/>}/>
                    <Route path="/devices" element={<Devices/>}/>
                </Routes>
            </main>
        </>
    );
}

export default App;