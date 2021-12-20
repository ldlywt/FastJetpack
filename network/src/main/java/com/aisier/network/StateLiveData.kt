package com.aisier.network.observer

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisier.network.ResultBuilder
import com.aisier.network.entity.*

/**
 * <pre>
 * @author : wutao
 * time   : 2021/10/18
 * desc   :
 * version: 1.1
</pre> *
 */
typealias StateLiveData<T> = LiveData<ApiResponse<T>>
typealias StateMutableLiveData<T> = MutableLiveData<ApiResponse<T>>

@MainThread
inline fun <T> StateMutableLiveData<T>.observeState(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    observe(owner) { apiResponse ->
        when (apiResponse) {
            is ApiSuccessResponse -> listener.onSuccess(apiResponse.response)
            is ApiEmptyResponse -> listener.onDataEmpty()
            is ApiFailedResponse -> listener.onFailed(apiResponse.errorCode, apiResponse.errorMsg)
            is ApiErrorResponse -> listener.onError(apiResponse.throwable)
        }
        listener.onComplete()
    }
}

@MainThread
inline fun <T> LiveData<ApiResponse<T>>.observeState(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    observe(owner) { apiResponse ->
        when (apiResponse) {
            is ApiSuccessResponse -> listener.onSuccess(apiResponse.response)
            is ApiEmptyResponse -> listener.onDataEmpty()
            is ApiFailedResponse -> listener.onFailed(apiResponse.errorCode, apiResponse.errorMsg)
            is ApiErrorResponse -> listener.onError(apiResponse.throwable)
        }
        listener.onComplete()
    }
}
