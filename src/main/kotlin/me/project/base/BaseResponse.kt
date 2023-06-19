package me.project.base

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val statusCode: Int,
    val message: String = "Success",
    val result: T?
)
