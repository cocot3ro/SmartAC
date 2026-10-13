package dev.cocot3ro.smartac.data.network.http.routing

import dev.cocot3ro.smartac.core.auth.AdminAuthRepository
import dev.cocot3ro.smartac.core.auth.AdminPasswordVerifier
import dev.cocot3ro.smartac.core.auth.AdminSession
import dev.cocot3ro.smartac.data.network.model.HealthResponse
import dev.cocot3ro.smartac.data.network.model.LoginRequest
import dev.cocot3ro.smartac.data.network.model.VersionCheckResponse
import dev.cocot3ro.smartac.data.network.model.VersionModel
import dev.cocot3ro.smartac.data.network.routes.HealthRoute
import dev.cocot3ro.smartac.data.network.routes.LoginRoute
import dev.cocot3ro.smartac.data.network.routes.LogoutRoute
import dev.cocot3ro.smartac.data.network.routes.SessionRoute
import dev.cocot3ro.smartac.data.network.routes.SetupRoute
import dev.cocot3ro.smartac.data.network.routes.VersionCheckRoute
import dev.cocot3ro.smartac.modules.adminSessionAuth
import dev.cocot3ro.smartac.utils.IsSetup
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.ktor.ext.inject

fun Route.adminRouting() {

    get<HealthRoute> { _: HealthRoute ->
        val isSetup by inject<IsSetup>()

        call.respond(
            message = HealthResponse(
                setup = isSetup(),
                // TODO: Replace with the real version
                version = VersionModel(major = 0, minor = 0, patch = 1)
            )
        )
    }

    get<VersionCheckRoute> { _: VersionCheckRoute ->
        call.respond(
            message = VersionCheckResponse(
                current = VersionModel(major = 0, minor = 0, patch = 1),
                latest = VersionModel(major = 0, minor = 0, patch = 2),
                updateAvailable = true
            )
        )
    }

    post<SetupRoute, LoginRequest> { _: SetupRoute, body: LoginRequest ->
        val isSetup by inject<IsSetup>()

        if (isSetup()) {
            call.respond(
                status = HttpStatusCode.NotAcceptable,
                message = "Admin password is already set up."
            )
            return@post
        }

        val adminAuthRepository by inject<AdminAuthRepository>()

        val success: Boolean = adminAuthRepository.setAdminPassword(body.password)

        if (!success) {
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Invalid password. Password must be at least 8 characters long " +
                        "and contain at least one uppercase letter, one lowercase letter, " +
                        "one digit, and one special character."
            )
        }

        call.respondRedirect(
            url = LoginRoute.getPath(),
            permanent = false
        )
    }

    post<LoginRoute, LoginRequest> { _: LoginRoute, body: LoginRequest ->

        val passwordVerifier by inject<AdminPasswordVerifier>()

        if (!passwordVerifier.verifyPassword(body.password)) {
            call.respond(message = HttpStatusCode.Unauthorized)
            return@post
        }

        call.sessions.clear<AdminSession>()
        call.sessions.set(value = AdminSession())

        call.respond(message = HttpStatusCode.OK)
    }

    adminSessionAuth {
        get<SessionRoute> { _: SessionRoute ->
            call.respond(HttpStatusCode.NoContent)
        }

        post<LogoutRoute> { _: LogoutRoute ->
            call.sessions.clear<AdminSession>()
            call.respond(message = HttpStatusCode.NoContent)
        }
    }
}
