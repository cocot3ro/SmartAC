import useLocalizeDocumentAttributes from "./i18n/useLocalizeDocumentAttributes.ts";
import {MemoryRouter, Route, Routes} from "react-router-dom";
import EasyModal from "ez-modal-react";
import {ThemeProvider} from "./context";
import {lazy} from "react";
import {ToastContainer} from "react-toastify";
import {Footer, Header} from "src/components";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import {useTranslation} from "react-i18next";

const Home = lazy(() => import("./pages/Home.tsx"));
const Users = lazy(() => import("./pages/Users.tsx"));
const Devices = lazy(() => import("./pages/Devices.tsx"));

export default function App() {

    const {i18n} = useTranslation();

    useLocalizeDocumentAttributes();

    const queryClient = new QueryClient();

    return (
        <ThemeProvider>
            <QueryClientProvider client={queryClient}>
                <>
                    <EasyModal.Provider>
                        <MemoryRouter initialEntries={["/"]}>
                            <div className={"page"}>
                                <div className={"page-wrapper"}>
                                    <Header/>
                                    <div>
                                        <Routes>
                                            <Route path="/" element={<Home/>}/>
                                            <Route path="/users" element={<Users/>}/>
                                            <Route path="/devices" element={<Devices/>}/>
                                        </Routes>

                                        <Footer/>
                                    </div>
                                </div>
                            </div>
                        </MemoryRouter>
                    </EasyModal.Provider>
                    <ToastContainer
                        aria-label={"Notification"}
                        position="top-right"
                        autoClose={5000}
                        hideProgressBar={true}
                        newestOnTop={true}
                        closeOnClick={true}
                        rtl={i18n.dir() === "rtl"}
                        closeButton={false}
                    />
                </>
                <ReactQueryDevtools buttonPosition="bottom-right" position="right"/>
            </QueryClientProvider>
        </ThemeProvider>
    );
}
