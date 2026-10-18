import * as api from "./base.ts";
import { LoginRequest, LoginRoute } from "webapp-shared";

export async function login(password: string): Promise<void> {
    return await api.post({
        url: LoginRoute.Companion.getPath(),
        data: new LoginRequest(password)
    });
}