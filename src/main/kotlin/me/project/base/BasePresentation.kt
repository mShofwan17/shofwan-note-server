package me.project.base

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

abstract class BasePresentation {
    private var _httpCode = HttpStatusCode.OK
    val httpCode get() = _httpCode

    private val dataIsNull = "Data tidak ditemukan"
    suspend fun tryResponse(
        call: PipelineContext<Unit, ApplicationCall>,
        block: suspend () -> Unit
    ) {
        try {
            _httpCode = HttpStatusCode.OK
            block.invoke()
        } catch (e: NotFoundException) {
            _httpCode = HttpStatusCode.NotFound
            call.call.respond(
                status = HttpStatusCode.NotFound,
                message = responseError(
                    exceptionMsg = e.message
                )
            )
        } catch (e: IllegalArgumentException) {
            _httpCode = HttpStatusCode.BadRequest
            call.call.respond(
                status = HttpStatusCode.BadRequest,
                message = responseError(
                    exceptionMsg = e.message
                )
            )
        } catch (e: Exception) {
            _httpCode = HttpStatusCode.InternalServerError
            call.call.respond(
                status = _httpCode,
                message = responseError(
                    exceptionMsg = e.message
                )
            )
        }
    }

     fun <T> responseSuccess(result: T?) = BaseResponse(
        statusCode = _httpCode.value,
        message = _httpCode.description,
        result
    )

    private fun responseError(
        isEmptyResult: Boolean = false,
        exceptionMsg: String? = null
    ): BaseResponse<String?> {
        if (isEmptyResult) {
            _httpCode = HttpStatusCode.NotFound
        }

        return BaseResponse(
            statusCode = _httpCode.value,
            message = _httpCode.description,
            result = exceptionMsg ?: dataIsNull
        )
    }

    suspend fun emptyResult(call: PipelineContext<Unit, ApplicationCall>) {
        call.call.respond(
            message = responseError(true),
            status = httpCode
        )
    }
}