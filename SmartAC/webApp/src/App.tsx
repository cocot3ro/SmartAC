import useLocalizeDocumentAttributes from "./i18n/useLocalizeDocumentAttributes.ts";
import {Route, Routes} from "react-router-dom";
import {lazy} from "react";
import Header from "./components/Header.tsx";
import Footer from "./components/Footer.tsx";

const Home = lazy(() => import("./pages/Home.tsx"));
const Users = lazy(() => import("./pages/Users.tsx"));
const Devices = lazy(() => import("./pages/Devices.tsx"));

export default function App() {
    useLocalizeDocumentAttributes();

    return (
        <>
            <div className={"page"}>
                <div className={"page-wrapper"}>
                    <div>
                        <Header/>

                        <Routes>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/users" element={<Users/>}/>
                            <Route path="/devices" element={<Devices/>}/>
                        </Routes>

                        <Footer/>
                    </div>
                </div>
            </div>
        </>
    );
}
