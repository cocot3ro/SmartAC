// noinspection JSUnresolvedReference,NpmUsedModulesInstalled

// Kotlin/JS emits the whole app as one module, which doesn't play well with webpack's production defaults.

;(function () {
    if (config.mode !== 'production') return;
    const TerserPlugin = require('terser-webpack-plugin');
    config.optimization = config.optimization || {};
    config.optimization.concatenateModules = false;
    config.optimization.minimizer = [
        new TerserPlugin({
            // SWC is natively multithreaded, even per-file, providing a speed boost.
            // It is added in build.gradle.kts as a devNpm dependency.
            minify: TerserPlugin.swcMinify,
        }),
    ];
})();
