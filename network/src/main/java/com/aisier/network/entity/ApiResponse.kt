package com.aisier.network.entity

import java.io.Serializable

open class ApiResponse<T>(
        open val data: T? = null,
        open val errorCode: Int? = null,
        open val errorMsg: String? = null,
        open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0

    override fun toString(): String {
        return "ApiResponse(data=$data, errorCode=$errorCode, errorMsg=$errorMsg, error=$error)"
    }


}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override val errorCode: Int?, override val errorMsg: String?) : ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
