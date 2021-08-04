package com.aisier.architecture.base

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
            return handleHttpOkResponse(data)
        }.onFailure { e ->
            e.printStackTrace()
            //非后台返回错误，捕获到的异常
            handlingExceptions(e)
            return ApiErrorResponse(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * Http 状态码200，请求成功，但是后台定义了一些错误码
     */
    private fun <T> handleHttpOkResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.isSuccess) {
            if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
                //TODO: 数据为空,结构变化时需要修改判空条件
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(response.data!!)
            }
        } else {
            handlingApiExceptions(response.errorCode, response.errorMsg)
            ApiFailedResponse(response.errorCode, response.errorMsg)
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