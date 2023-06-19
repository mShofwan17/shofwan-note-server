package me.project.base

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

abstract class BasePresentation {
    private var _httpCode = HttpStatusCode.OK
    private val dataIsNull = "Data tidak ditemukan"
    suspend fun tryResponse(
        call: PipelineContext<Unit, ApplicationCall>,
        block: suspend (httpCode : HttpStatusCode) -> Unit
    ) {
        try {
            _httpCode = HttpStatusCode.OK
            block.invoke(_httpCode)
        } catch (e: NotFoundException) {
            _httpCode = HttpStatusCode.NotFound
            call.call.respond(
                status = HttpStatusCode.NotFound,
                message = onError(
                    exceptionMsg = e.message
                )
            )
        } catch (e: IllegalArgumentException) {
            _httpCode = HttpStatusCode.BadRequest
            call.call.respond(
                status = HttpStatusCode.BadRequest,
                message = onError(
                    exceptionMsg = e.message
                )
            )
        } catch (e: Exception) {
            _httpCode = HttpStatusCode.InternalServerError
            call.call.respond(
                status = _httpCode,
                message = onError(
                    exceptionMsg = e.message
                )
            )
        }
    }

     fun <T> onSuccess(result: T?) = BaseResponse(
        statusCode = _httpCode.value,
        message = _httpCode.description,
        result
    )

    private fun onError(
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

    suspend fun ApplicationCall.emptyResult() {
        respond(
            message = onError(true),
            status = _httpCode
        )
    }

    suspend fun ApplicationCall.failedUpdateData(id : Int?){
        _httpCode = HttpStatusCode.BadRequest
        respond(
            message = onError(exceptionMsg = "Gagal Update Data Note id : $id"),
            status = _httpCode
        )
    }
}