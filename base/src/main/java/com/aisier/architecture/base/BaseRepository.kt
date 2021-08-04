package com.aisier.architecture.base

import com.aisier.architecture.BuildConfig
import com.aisier.architecture.entity.*
import com.aisier.architecture.util.toast
import com.google.gson.JsonParseException
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        //for test
        delay(500)
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        //非后台返回错误，捕获到的异常
        handlingExceptions(e)
        return ApiErrorResponse(e)
    }

    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            handlingApiExceptions(data.errorCode, data.errorMsg)
            ApiFailedResponse(data.errorCode, data.errorMsg)
        }
    }

    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(response.data!!)
        }
    }

    private fun handlingExceptions(e: Throwable) = when (e) {
        is HttpException -> {
            toast(e.message())
        }
        is CancellationException -> {
        }
        is SocketTimeoutException -> {
        }
        is JsonParseException -> {
        }
        else -> {
        }
    }

}