import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.libres)
}

group = "dev.cocot3ro.smartac"
version = providers.gradleProperty("serverSiteVersion").get()

kobweb {
    app {
        index {
            description = "SmartAC Admin panel"

            head.add {
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/@tabler/core@1.7.1/dist/css/tabler.min.css"
                    integrity =
                        "sha384-FwFRx3iCRjX2MYq9Ni5guhu0G/HAeKcgZhAMxMtZkceAylYMpaOn6hHeId2bywU9"
                    attributes["crossorigin"] = "anonymous"
                }

                script {
                    src = "https://cdn.jsdelivr.net/npm/@tabler/core@1.7.1/dist/js/tabler.min.js"
                    integrity =
                        "sha384-eqBhoRQcupNldY9cDRl77mvk57Y7DWYa+nVjNzLLwtwVynw+RpM/FotWDnIODMwM"
                    attributes["crossorigin"] = "anonymous"
                    defer = true
                }

                link {
                    rel = "stylesheet"
                    href =
                        "https://cdn.jsdelivr.net/npm/@tabler/core@1.7.4/dist/css/tabler-flags.min.css"
                    integrity =
                        "sha384-dY8vZEpxhN02an00T8773o04+plkVOIEUZloG9zwuUB6gZQwuu/mt0qWuB6dWqHG"
                    attributes["crossorigin"] = "anonymous"
                }
            }
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
            api(projects.clientShared)

            implementation(libs.wrappers.browser)

            implementation(libs.compose.runtime)
            implementation(libs.compose.html)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.web)

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
    generateInternalResourceClasses = true
    baseLocaleTag = "en"
}
