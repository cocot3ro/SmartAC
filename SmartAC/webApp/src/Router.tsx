import { BrowserRouter, Route, Routes } from "react-router-dom";
import { lazy, Suspense } from "react";
import { ErrorNotFound, Footer, Header, LoadingPage, Page, SiteContainer } from "src/components";
// import { useHealth } from "src/hooks";
// import { useAuthState } from "src/context";

// const Setup = lazy(() => import("src/pages/Setup"));
// const Unhealthy = lazy(() => import("src/pages/Unhealthy"));
// const Login = lazy(() => import("src/pages/Login"));
const Dashboard = lazy(() => import("src/pages/Dashboard"));
const Users = lazy(() => import("src/pages/Users"));
const Devices = lazy(() => import("src/pages/Devices"));

function Router() {

    // const health = useHealth();
    // const { authenticated } = useAuthState();

    // if (health.isLoading) {
    //     return <LoadingPage />;
    // }
    //
    // if (health.isError) {
    //     return <Unhealthy />;
    // }
    //
    // if (!health.data?.setup) {
    //     return <Setup />;
    // }
    //
    // if (authenticated == null) {
    //     return <LoadingPage />;
    // }
    //
    // if (!authenticated) {
    //     return (
    //         <Suspense fallback={ <LoadingPage /> }>
    //             <Login />
    //         </Suspense>
    //     );
    // }

    return (
        <BrowserRouter>
            <Page>
                <Header />
                <SiteContainer>
                    <Suspense fallback={ <LoadingPage /> }>
                        <Routes>
                            <Route path={ "*" } element={ <ErrorNotFound /> } />
                            <Route path={ "/users" } element={ <Users /> } />
                            <Route path={ "/devices" } element={ <Devices /> } />
                            <Route path={ "/" } element={ <Dashboard /> } />
                        </Routes>
                    </Suspense>
                </SiteContainer>
                <Footer />
            </Page>
        </BrowserRouter>
    );
}

export default Router;
