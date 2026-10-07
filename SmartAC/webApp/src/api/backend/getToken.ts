import * as api from "./base";
import { TokensRoute, type TokenResponse } from "webapp-shared";

export async function getToken(identity: string, secret: string): Promise<TokenResponse> {
	return await api.post({
		url: TokensRoute.Companion.getPath(),
		data: { identity, secret },
	});
}
