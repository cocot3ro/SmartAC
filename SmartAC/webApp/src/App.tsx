import { isRtl, useLocalizeDocumentAttributes } from "src/i18n";
import EasyModal from "ez-modal-react";
import { AuthProvider, ThemeProvider } from "src/context";
import { ToastContainer } from "react-toastify";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import Router from "src/Router.tsx";

export default function App() {

    useLocalizeDocumentAttributes();

    const queryClient = new QueryClient();

    return (
        <ThemeProvider>
            <QueryClientProvider client={ queryClient }>
                <AuthProvider>
                    <EasyModal.Provider>
                        <Router />
                    </EasyModal.Provider>
                    <ToastContainer
                        aria-label={ "Notification" }
                        position={ "top-right" }
                        autoClose={ 5000 }
                        hideProgressBar={ true }
                        newestOnTop={ true }
                        closeOnClick={ true }
                        rtl={ isRtl() }
                        closeButton={ false }
                    />
                </AuthProvider>
                <ReactQueryDevtools buttonPosition={ "bottom-right" } position={ "right" } />
            </QueryClientProvider>
        </ThemeProvider>
    );
}
