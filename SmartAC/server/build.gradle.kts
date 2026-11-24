import org.gradle.kotlin.dsl.kotlin

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
}

group = "dev.cocot3ro.smartac"
version = providers.gradleProperty("serverSiteVersion").get()

application {
    mainClass = "dev.cocot3ro.smartac.MainKt"
}

dependencies {
    implementation(projects.siteShared)
    implementation(projects.appShared)

    implementation(libs.logback.classic)

    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.resources)
    implementation(ktorLibs.server.websockets)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.auth.jwt)
    implementation(ktorLibs.server.cors)

    implementation(libs.androidx.room3.runtime)
    implementation(libs.androidx.sqlite.bundled)

    ksp(libs.androidx.room3.compiler)

    implementation(libs.ukemp.ktor.mqtt.core)
    implementation(libs.ukemp.ktor.mqtt.client)

    implementation(libs.kotlinx.datetime)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.bundles.koin.server)

    implementation(libs.argon2.jvm)

    testImplementation(libs.kotlin.testJunit)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir(
            layout.buildDirectory
                .dir("generated/source/buildConfig/main/kotlin")
                .get().asFile.toString()
        )
    }
}

tasks.configureEach {
    if (
        name.startsWith("ksp") ||
        name.startsWith("compileKotlin")
    ) {
        dependsOn(generateBuildConfig)
    }
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
