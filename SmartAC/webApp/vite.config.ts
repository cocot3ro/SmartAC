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

                // dist/index.html                             0.83 kB │ gzip:   0.39 kB
                // dist/assets/index-BjLBu27R.css              3.48 kB │ gzip:   0.93 kB
                // dist/assets/ui-vendor-DcCdPG_7.css        637.14 kB │ gzip:  78.92 kB
                // dist/assets/Devices-9CYlELs3.js             0.16 kB │ gzip:   0.14 kB
                // dist/assets/Users-IhK4sxQA.js               0.16 kB │ gzip:   0.14 kB
                // dist/assets/Dashboard-CS2DafbD.js           0.34 kB │ gzip:   0.24 kB
                // dist/assets/rolldown-runtime-hePW80VL.js    0.71 kB │ gzip:   0.42 kB
                // dist/assets/ui-vendor-2R-loEby.js         166.01 kB │ gzip:  48.81 kB
                // dist/assets/react-vendor-CyDUuctK.js      218.83 kB │ gzip:  68.25 kB
                // dist/assets/vendor-CtclnS2W.js            388.40 kB │ gzip: 102.58 kB
                // dist/assets/index-DdhsP_eg.js             535.79 kB │ gzip: 114.25 kB

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