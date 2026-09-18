import useLocalizeDocumentAttributes from "./i18n/useLocalizeDocumentAttributes.ts";
import {Route, Routes} from "react-router-dom";
import Header from "./components/Header.tsx";
import Home from "./pages/Home.tsx";
import Users from "./pages/Users.tsx";
import Devices from "./pages/Devices.tsx";
import Footer from "./components/Footer.tsx";

export default function App() {
    useLocalizeDocumentAttributes();

    return (
        <div className={"page"}>
            <div className={"page-wrapper"}>
                <div style={{display: "flex", flexDirection: "column", minHeight: "100vh"}}>
                    <Header/>

                    <div className="container-xl py-4" style={{flex: 1}}>
                        <Routes>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/users" element={<Users/>}/>
                            <Route path="/devices" element={<Devices/>}/>
                        </Routes>
                    </div>

                    <Footer/>
                </div>
            </div>
        </div>
    );
}
