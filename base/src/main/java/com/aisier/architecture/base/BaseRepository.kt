package com.aisier.architecture.base

import com.aisier.architecture.entity.*
import kotlinx.coroutines.delay

open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> IBaseResponse<T>): IBaseResponse<T> {
        var response: IBaseResponse<T> = BaseResponse()
        //for test
        delay(1000)
        runCatching {
            response = block.invoke()
        }.onSuccess {
            handleHttpOkResponse(response)
        }.onFailure { e ->
            e.printStackTrace()
            //非后台返回错误，捕获到的异常
            response.dataState = DataState.STATE_ERROR
            response.error = e
            handlingExceptions(e)
        }
        return response
    }

    /**
     * Http 状态码200，请求成功，但是后台定义了一些错误码
     */
    private fun <T> handleHttpOkResponse(baseResp: IBaseResponse<T>) {
        if (baseResp.isSuccess) {
            if (baseResp.httpData == null || baseResp.httpData is List<*> && (baseResp.httpData as List<*>).isEmpty()) {
                //TODO: 数据为空,结构变化时需要修改判空条件
                baseResp.dataState = DataState.STATE_EMPTY
            } else {
                baseResp.dataState = DataState.STATE_SUCCESS
            }
        } else {
            handlingApiExceptions(baseResp.httpCode, baseResp.httpMsg)
            baseResp.dataState = DataState.STATE_FAILED
        }
    }


}