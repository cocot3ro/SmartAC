import { SessionRoute } from "webapp-shared";
import * as api from "./base.ts";

export async function checkSession(): Promise<void> {
    await api.get({
        url: SessionRoute.Companion.getPath()
    });
}
