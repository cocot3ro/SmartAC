import * as api from "src/api/backend/base.ts";
import { type UserModel, UsersRoute } from "webapp-shared";

export async function getUsers(): Promise<UserModel[]> {
    return await api.get({
        url: UsersRoute.Companion.getPath()
    });
}
