import {BrowserRouter, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import {ErrorNotFound, Footer, Header, LoadingPage, Page, SiteContainer} from "src/components";

const Users = lazy(() => import("src/pages/Users"));
const Devices = lazy(() => import("src/pages/Devices"));
const Dashboard = lazy(() => import("src/pages/Dashboard"));

function Router() {
    return (
        <BrowserRouter>
            <Page>
                <Header/>
                <SiteContainer>
                    <Suspense fallback={<LoadingPage/>}>
                        <Routes>
                            <Route path="*" element={<ErrorNotFound/>}/>
                            <Route path={"/users"} element={<Users/>}/>
                            <Route path={"/devices"} element={<Devices/>}/>
                            <Route path={"/"} element={<Dashboard/>}/>
                        </Routes>
                    </Suspense>
                </SiteContainer>
                <Footer/>
            </Page>
        </BrowserRouter>
    );
}

export {Router};
