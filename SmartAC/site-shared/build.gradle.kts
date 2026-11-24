plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

val generateBuildConfig = tasks.register("generateBuildConfig") {
    group = "build"
    description = "Generates BuildConfig with project information."

    val outputDir = layout.buildDirectory.dir("generated/source/buildConfig/main/kotlin")
    val buildVersion = version.toString()

    outputs.dir(outputDir)

    doLast {
        val file = outputDir.get()
            .dir("com")
            .dir("cocot3ro")
            .dir("smartac")
            .dir("generated")
            .file("BuildConfig.kt").asFile

        file.parentFile.mkdirs()
        file.writeText(
            """
            package com.cocot3ro.smartac.generated

            object BuildConfig {
                const val VERSION = "$buildVersion"
            }
            """.trimIndent()
        )
    }
}

kotlin {
    jvm()

    js {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain {

            kotlin.srcDir(generateBuildConfig)

            dependencies {
                api(projects.core)

                implementation(libs.kotlinx.serialization.core)

                implementation(ktorLibs.serialization.kotlinx.json)
                implementation(ktorLibs.resources)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
