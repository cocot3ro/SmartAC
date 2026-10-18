import { useQueryClient } from "@tanstack/react-query";
import { createContext, type ReactNode, useContext, useState } from "react";
import { useIntervalWhen } from "rooks";
import { checkSession, login, logout } from "src/api/backend";

// Context
export interface AuthContextType {
    authenticated: boolean;
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
    const [ authenticated, setAuthenticated ] = useState<boolean | undefined>(undefined);

    const clear = async () => {
        setAuthenticated(false);
        queryClient.clear();
        await queryClient.refetchQueries({ queryKey: [ "health" ] });
    }

    const refresh = async () => {
        checkSession()
            .then(() => setAuthenticated(true))
            .catch(() => clear());
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

    const onLogout = async () => {
        await logout();
        await clear();
    };

    const onLogin = async (secret: string) => {
        login(secret)
            .then(() => setAuthenticated(true))
            .catch(() => clear());
    };

    const value = {
        authenticated: authenticated === true,
        login: onLogin,
        logout: onLogout
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
