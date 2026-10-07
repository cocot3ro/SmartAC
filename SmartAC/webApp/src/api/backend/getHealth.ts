import * as api from "./base";
import {HealthRoute, type HealthResponse} from "webapp-shared";

export async function getHealth(): Promise<HealthResponse> {
    return await api.get({
        url: HealthRoute.Companion.getPath(),
    });
}
