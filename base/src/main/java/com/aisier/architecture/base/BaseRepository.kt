package com.aisier.architecture.base

import com.aisier.architecture.entity.ApiResponse
import com.aisier.architecture.entity.ResState
import com.aisier.architecture.entity.handlingApiExceptions
import com.aisier.architecture.entity.handlingExceptions
import com.aisier.architecture.net.BaseResp
import com.aisier.architecture.net.DataState
import com.aisier.architecture.net.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> executeResp(
        resp: ApiResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null,
    ): ResState<T> {
        return coroutineScope {
            if (resp.errorCode == 0) {
                successBlock?.let { it() }
                ResState.Success(resp.data)
            } else {
                errorBlock?.let { it() } ?: handlingApiExceptions(resp.errorCode, resp.errorMsg)
                ResState.Error(IOException(resp.errorMsg))
            }
        }
    }

    /**
     * repo 请求数据的公共方法，
     * 在不同状态下先设置 baseResp.dataState的值，最后将dataState 的状态通知给UI
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeResp(
        block: suspend () -> BaseResp<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var baseResp = BaseResp<T>()
        try {
            baseResp.dataState = DataState.STATE_LOADING
            stateLiveData.postValue(baseResp)
            delay(1000)
            baseResp = block.invoke()
            handleHttpOkResponse(baseResp)
        } catch (e: Exception) {
            //非后台返回错误，捕获到的异常
            baseResp.dataState = DataState.STATE_ERROR
            baseResp.error = e
            handlingExceptions(e)
        } finally {
            stateLiveData.postValue(baseResp)
        }
    }

    /**
     * Http 状态码200，请求成功，但是后台定义了一些错误码
     */
    private fun <T : Any> handleHttpOkResponse(baseResp: BaseResp<T>) {
        if (baseResp.isSuccess) {
            //请求成功，判断数据是否为空，
            //因为数据有多种类型，需要自己设置类型进行判断
            if (baseResp.data == null || baseResp.data is List<*> && (baseResp.data as List<*>).size == 0) {
                //TODO: 数据为空,结构变化时需要修改判空条件
                baseResp.dataState = DataState.STATE_EMPTY
            } else {
                //请求成功并且数据为空的情况下，为STATE_SUCCESS
                baseResp.dataState = DataState.STATE_SUCCESS
            }
        } else {
            handlingApiExceptions(baseResp.errorCode, baseResp.errorMsg)
            baseResp.dataState = DataState.STATE_FAILED
        }
    }


}