import {MemoryRouter, Route, Routes} from "react-router-dom";
import Users from "src/pages/Users";
import Devices from "src/pages/Devices";
import {Footer, Header} from "src/components";

function Router() {
    return (
        <MemoryRouter initialEntries={["/"]}>
            <div className={"page"}>
                <div className={"page-wrapper"}>
                    <Header/>
                    <div>
                        <Routes>
                            <Route path={"/"} element={<div>Home</div>}/>
                            <Route path={"/users"} element={<Users/>}/>
                            <Route path={"/devices"} element={<Devices/>}/>
                        </Routes>

                        <Footer/>
                    </div>
                </div>
            </div>
        </MemoryRouter>
    );
}

export {Router};
