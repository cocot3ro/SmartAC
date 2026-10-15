import * as api from "./base.ts";
import { type LoginRequest, LoginRoute } from "webapp-shared";

export async function setup(loginRequest: LoginRequest): Promise<void> {
    return await api.post({
        url: LoginRoute.Companion.getPath(),
        data: loginRequest
    });
}