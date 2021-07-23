package com.aisier.architecture.entity

import java.io.Serializable

open class ApiResponse<T>(
        var data: T? = null,
        var errorCode: Int? = null,
        var errorMsg: String? = null,
        var error: Throwable? = null
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0
}

data class ApiSuccessResponse<T>(val httpData: T?) : ApiResponse<T>(data = httpData)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(val code: Int?, val msg: String?) : ApiResponse<T>(errorCode = code, errorMsg = msg)

data class ApiErrorResponse<T>(val e: Throwable?) : ApiResponse<T>(error = e)
