package com.aisier.architecture.entity

data class ApiResponse<out T>(val errorCode: Int, val errorMsg: String?, val data: T)

sealed class ResState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResState<T>()
    data class Error(val exception: Exception) : ResState<Nothing>()
}
