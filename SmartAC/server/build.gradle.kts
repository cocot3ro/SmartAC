plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ksp)
}

group = "dev.cocot3ro.smartac"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(projects.shared)

    implementation(libs.logback)

    implementation(platform(libs.ktor.bom))
    implementation(libs.bundles.ktor.server)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.server)

    implementation(libs.hivemq.mqtt.client)

    api(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.testJunit)
}
