import * as api from "./base";
import {type TokenResponse} from "webapp-shared";

export async function refreshToken(): Promise<TokenResponse> {
    return await api.get({
        url: "/tokens",
    });
}
