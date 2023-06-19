package me.project.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import me.project.base.BaseResponse

fun Application.configStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                message = BaseResponse(
                    statusCode = HttpStatusCode.InternalServerError.value,
                    message = "500: $cause",
                    result = "500: $cause"
                ),
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}