import * as api from "./base.ts";
import { LogoutRoute } from "webapp-shared";

export async function logout(): Promise<void> {
    return await api.post({
        url: LogoutRoute.Companion.getPath()
    });
}