package com.aisier.architecture.base

import com.aisier.architecture.entity.*
import kotlinx.coroutines.delay

open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        var response: ApiResponse<T> = ApiResponse()
        //for test
        delay(500)
        runCatching {
            response = block.invoke()
        }.onSuccess {
            return handleHttpOkResponse(response)
        }.onFailure { e ->
            e.printStackTrace()
            //非后台返回错误，捕获到的异常
            handlingExceptions(e)
            return ApiErrorResponse(e)
        }
        return response
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
                ApiSuccessResponse(response.data)
            }
        } else {
            handlingApiExceptions(response.errorCode, response.errorMsg)
            ApiFailedResponse(response.errorCode, response.errorMsg)
        }
    }
}