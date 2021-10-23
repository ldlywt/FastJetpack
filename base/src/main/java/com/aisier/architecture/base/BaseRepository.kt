package com.aisier.architecture.base

import com.aisier.architecture.entity.*
import com.aisier.architecture.net.StateLiveData
import com.aisier.architecture.net.handlingApiExceptions
import com.aisier.architecture.net.handlingExceptions
import kotlinx.coroutines.delay

open class BaseRepository {
    /**
     * repo 请求数据的公共方法，
     * 在不同状态下先设置 baseResp.dataState的值，最后将dataState 的状态通知给UI
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeResp(stateLiveData: StateLiveData<T>, block: suspend () -> ApiResponse<T>) {
        var baseResp: ApiResponse<T> = ApiResponse()
        stateLiveData.postValue(ApiLoadingResponse())
        //for test
        delay(500)
        runCatching {
            baseResp = block.invoke()
        }.onSuccess {
            baseResp = handleHttpOkResponse(baseResp)
        }.onFailure { e ->
            e.printStackTrace()
            //非后台返回错误，捕获到的异常
            baseResp = ApiErrorResponse(e)
            handlingExceptions(e)
        }
        stateLiveData.postValue(baseResp)
    }

    /**
     * Http 状态码200，请求成功，但是后台定义了一些错误码
     */
    private fun <T : Any> handleHttpOkResponse(baseResp: ApiResponse<T>): ApiResponse<T> {
        if (baseResp.isSuccess) {
            return if (baseResp.data == null || baseResp.data is List<*> && (baseResp.data as List<*>).isEmpty()) {
                //TODO: 数据为空,结构变化时需要修改判空条件
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(baseResp.data!!)
            }
        }
        handlingApiExceptions(baseResp.errorCode, baseResp.errorMsg)
        return ApiFailedResponse(baseResp.errorCode, baseResp.errorMsg)
    }


}