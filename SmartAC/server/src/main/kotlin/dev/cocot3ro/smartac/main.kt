package dev.cocot3ro.smartac

import dev.cocot3ro.smartac.modules.configureCors
import dev.cocot3ro.smartac.modules.configureKoin
import dev.cocot3ro.smartac.modules.configureResources
import dev.cocot3ro.smartac.modules.configureRouting
import dev.cocot3ro.smartac.modules.configureSecurity
import dev.cocot3ro.smartac.modules.configureSerialization
import dev.cocot3ro.smartac.modules.configureSessions
import dev.cocot3ro.smartac.modules.configureWebsockets
import io.ktor.server.application.Application
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {

    embeddedServer(
        factory = Netty,
        module = Application::modules,
        configure = {
            connector {
                host = "0.0.0.0"
                port = 8080
            }

            connector {
                host = "0.0.0.0"
                port = 8081
            }
        }
    ).start(wait = true)
}

suspend fun Application.modules() {
    configureSerialization()
    configureCors()
    configureResources()
    configureKoin()
    configureSessions()
    configureSecurity()
    configureWebsockets()
    configureRouting()
}
