import * as api from "./base.ts";
import { LoginRequest, SetupRoute } from "webapp-shared";

export async function setup(password: string): Promise<void> {
    return await api.post({
        url: SetupRoute.Companion.getPath(),
        data: new LoginRequest(password)
    });
}