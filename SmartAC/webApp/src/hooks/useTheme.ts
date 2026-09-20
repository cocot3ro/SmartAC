import { Dark, Light, useTheme as useThemeContext } from "../context/ThemeContext";

// Simple hook wrapper for clarity and scalability
const useTheme = () => {
    return useThemeContext();
};

export { useTheme, Dark, Light };