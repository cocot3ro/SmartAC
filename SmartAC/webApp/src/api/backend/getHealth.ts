import * as api from "./base.ts";
import { type HealthResponse, HealthRoute } from "webapp-shared";

export async function getHealth(): Promise<HealthResponse> {
    return await api.get({
        url: HealthRoute.Companion.getPath()
    });
}
