package dev.cocot3ro.smartac.modules

import dev.cocot3ro.smartac.di.SmartACKoinApplication
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.environmentProperties
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.plugin.module.dsl.withConfiguration

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        environmentProperties()
        withConfiguration<SmartACKoinApplication>()
    }
}
