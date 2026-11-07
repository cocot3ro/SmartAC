import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.libres)
}

group = "dev.cocot3ro.smartac"
version = "0.0.1"

kobweb {
    app {
        index {
            description = "SmartAC Admin panel"
        }
    }
}

kotlin {
    configAsKobwebApplication("smartac")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
        }

        jsMain.dependencies {
            implementation(projects.siteShared)

            implementation(libs.compose.runtime)
            implementation(libs.compose.html)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)

            implementation(libs.libres)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.client)

            implementation(ktorLibs.client.core)
            implementation(ktorLibs.client.contentNegotiation)
            implementation(ktorLibs.client.resources)
            implementation(ktorLibs.serialization.kotlinx.json)
            implementation(ktorLibs.client.js)

            implementation(devNpm("@swc/core", "1.16.1"))
            implementation(npm("esptool-js", "0.7.0"))
        }
    }
}

libres {
    generatedClassName = "Res"
    generateNamedArguments = true
    baseLocaleLanguageCode = "es"
}
