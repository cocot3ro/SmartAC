import { useQueryClient } from "@tanstack/react-query";
import { createContext, type ReactNode, useContext, useState } from "react";
import { useIntervalWhen } from "rooks";
import { checkSession } from "src/api/backend";

// Context
export interface AuthContextType {
    authenticated: boolean | undefined;
    login: (password: string) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

// Provider
interface Props {
    children?: ReactNode;
    sessionRefreshInterval?: number;
}

function AuthProvider({ children, sessionRefreshInterval = 5 * 60 * 1000 }: Props) {
    const queryClient = useQueryClient();
    const [ authenticated, setAuthenticated ] = useState<boolean>();

    const refresh = async () => {
        checkSession()
            .catch(() => logout())
            .then(() => setAuthenticated(true));
    };

    useIntervalWhen(
        () => {
            if (authenticated !== false) {
                // noinspection JSIgnoredPromiseFromCall
                refresh();
            }
        },
        sessionRefreshInterval,
        true,
        true
    );

    const logout = () => {
        console.log("Logging out");
        setAuthenticated(false);
        queryClient.clear();
    };

    const login = async (secret: string) => {
        login(secret)
            .catch(() => logout())
            .then(() => setAuthenticated(true));
    };

    const value = {
        authenticated,
        login,
        logout
    };

    return <AuthContext.Provider value={ value }>{ children }</AuthContext.Provider>;
}

function useAuthState() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuthState must be used within a AuthProvider");
    }
    return context;
}

export { AuthProvider, useAuthState };
export default AuthContext;
