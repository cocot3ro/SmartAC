import {useLocalizeDocumentAttributes} from "src/i18n";
import EasyModal from "ez-modal-react";
import {ThemeProvider} from "src/context";
import {ToastContainer} from "react-toastify";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import {useTranslation} from "react-i18next";
import {Router} from "src/Router.tsx";

export default function App() {

    const {i18n} = useTranslation();

    useLocalizeDocumentAttributes();

    const queryClient = new QueryClient();

    return (
        <ThemeProvider>
            <QueryClientProvider client={queryClient}>
                <>
                    <EasyModal.Provider>
                        <Router/>
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
