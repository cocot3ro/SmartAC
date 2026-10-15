import { ADMIN_PASSWORD_REGEX } from "webapp-shared";

const validatePassword = () => {
    return (value: string): string | undefined => {
        if (!RegExp(ADMIN_PASSWORD_REGEX.get()).test(value)) {
            // TODO: Change to propper error message and localize
            return "Invalid password";
        }
    };
};

export { validatePassword };