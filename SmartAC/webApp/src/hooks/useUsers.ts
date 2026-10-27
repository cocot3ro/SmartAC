import { useQuery } from "@tanstack/react-query";
import { getUsers } from "src/api/backend";
import { type UserModel } from "webapp-shared";

const fetchUsers = () => getUsers();

const useUsers = (options = {}) => {
    return useQuery<UserModel[], Error>({
        queryKey: [ "users" ],
        queryFn: fetchUsers,
        refetchOnWindowFocus: false,
        retry: 5,
        refetchInterval: 15 * 1000, // 15 seconds
        staleTime: 14 * 1000, // 14 seconds
        ...options
    });
};

export { fetchUsers, useUsers };
