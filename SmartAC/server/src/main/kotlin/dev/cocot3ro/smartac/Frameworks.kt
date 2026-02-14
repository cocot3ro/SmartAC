package dev.cocot3ro.smartac

import dev.cocot3ro.smartac.di.networkModule
import dev.cocot3ro.smartac.di.stateModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.environmentProperties
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()

        environmentProperties()

        modules(
            networkModule,
            stateModule
        )
    }
}
