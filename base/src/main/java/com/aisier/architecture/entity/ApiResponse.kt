package com.aisier.architecture.entity

data class ApiResponse<out T>(val errorCode: Int, val errorMsg: String?, val data: T?)

sealed class HttpResponse

data class Success<out T>(val data: T) : HttpResponse()

data class Failure(val errorCode: Int, val errorMsg: String?) : HttpResponse()



