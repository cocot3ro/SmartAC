import {BrowserRouter, Route, Routes} from "react-router-dom";
import {lazy} from "react";
import {ErrorNotFound, Footer, Header} from "src/components";

const Users = lazy(() => import("src/pages/Users"));
const Devices = lazy(() => import("src/pages/Devices"));
const Dashboard = lazy(() => import("src/pages/Dashboard"));

function Router() {
    return (
        <BrowserRouter>
            <div className={"page"}>
                <div className={"page-wrapper"}>
                    <Header/>
                    <div>
                        <Routes>
                            <Route path="*" element={<ErrorNotFound />} />
                            <Route path={"/users"} element={<Users/>}/>
                            <Route path={"/devices"} element={<Devices/>}/>
                            <Route path={"/"} element={<Dashboard/>}/>
                        </Routes>

                        <Footer/>
                    </div>
                </div>
            </div>
        </BrowserRouter>
    );
}

export {Router};
