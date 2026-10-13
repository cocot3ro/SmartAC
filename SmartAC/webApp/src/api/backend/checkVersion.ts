import * as api from "./base.ts";
import { type VersionCheckResponse, VersionCheckRoute } from "webapp-shared";

export async function checkVersion(): Promise<VersionCheckResponse> {
    return await api.get({
        url: VersionCheckRoute.Companion.getPath()
    });
}