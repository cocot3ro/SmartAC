import "./App.css";
import {Routes, Route, useNavigate} from "react-router-dom";
import {CssBaseline, Toolbar, Button} from "@mui/material";
import useLocalizeDocumentAttributes from "./i18n/useLocalizeDocumentAttributes.ts";
import Devices from "./Pages/Devices/Devices.tsx";
import Users from "./Pages/Users/Users.tsx";
import Footer from "./components/Footer/Footer.tsx";

function App() {

    let navigate = useNavigate();

    useLocalizeDocumentAttributes();

    return (
        <>
            <CssBaseline/>
            <Toolbar className="navbar">
                <Button variant={"contained"} onClick={() => navigate("/users")}>Users</Button>
                <Button variant={"contained"} onClick={() => navigate("/devices")}>Devices</Button>
            </Toolbar>
            <main className={"main-content"}>
                <Routes>
                    <Route path="/users" element={<Users/>}/>
                    <Route path="/devices" element={<Devices/>}/>
                </Routes>
            </main>
            <Footer/>
        </>
    );
}

export default App;
