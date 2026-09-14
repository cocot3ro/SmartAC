plugins {
    alias(libs.plugins.kotlinJvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
}

group = "dev.cocot3ro.smartac"
version = "1.0.0"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    api(projects.core)
    implementation(projects.webAppShared)

    implementation(libs.logback.classic)

    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.resources)
    implementation(ktorLibs.server.websockets)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.cors)

    implementation(libs.ukemp.ktor.mqtt.core)
    implementation(libs.ukemp.ktor.mqtt.client)

    implementation(libs.kotlinx.datetime)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.bundles.koin.server)

    testImplementation(libs.kotlin.testJunit)
}