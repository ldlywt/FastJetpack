package com.aisier.architecture.entity

import java.io.Serializable

open class ApiResponse<T>(
        open var data: T? = null,
        open var errorCode: Int? = null,
        open var errorMsg: String? = null,
        open var error: Throwable? = null
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0
}

data class ApiSuccessResponse<T>(var response: T) : ApiResponse<T>(data = response)

class ApiLoadingResponse<T> : ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override var errorCode: Int?, override var errorMsg: String?) : ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)

data class ApiErrorResponse<T>(override var error: Throwable?) : ApiResponse<T>(error = error)
