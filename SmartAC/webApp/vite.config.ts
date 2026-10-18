import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    root: ".",
    plugins: [ react() ],
    build: {
        outDir: "dist",
        emptyOutDir: true,
        rolldownOptions: {
            output: {
                codeSplitting: {
                    groups: [
                        {
                            name: "react-vendor",
                            test: /node_modules[\\/](react|react-dom|scheduler)[\\/]/,
                            priority: 30
                        },
                        {
                            name: "ui-vendor",
                            test: /node_modules[\\/](@tabler|@tanstack)[\\/]/,
                            priority: 20
                        },
                        {
                            name: "vendor",
                            test: /node_modules[\\/]/,
                            priority: 10
                        },
                        {
                            name: "index",
                            test: /src[\\/]/,
                            priority: 0,
                            maxSize: 500 * 1024, // 500 KB
                        }
                    ]
                }
            }
        }
    },
    server: { port: 8081 },
    resolve: {
        tsconfigPaths: true
    }
});