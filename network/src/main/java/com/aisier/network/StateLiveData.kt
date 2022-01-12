package com.aisier.network

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisier.network.entity.ApiResponse

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
fun <T> StateMutableLiveData<T>.observeState(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    observe(owner) { apiResponse -> apiResponse.parseData(listenerBuilder) }
}

@MainThread
fun <T> LiveData<ApiResponse<T>>.observeState(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    observe(owner) { apiResponse -> apiResponse.parseData(listenerBuilder) }
}