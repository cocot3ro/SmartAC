plugins {
    alias(libs.plugins.kotlinJvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.sqldelight)
}

group = "dev.cocot3ro.smartac"
version = "0.0.1"

application {
    mainClass = "dev.cocot3ro.smartac.MainKt"
}

dependencies {
    api(projects.core)
    implementation(projects.webAppShared)

    implementation(libs.logback.classic)

    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.resources)
    implementation(ktorLibs.server.websockets)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.cors)

    implementation(libs.sqldelight.sqlite.driver)

    implementation(libs.ukemp.ktor.mqtt.core)
    implementation(libs.ukemp.ktor.mqtt.client)

    implementation(libs.kotlinx.datetime)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.bundles.koin.server)

    testImplementation(libs.kotlin.testJunit)
}