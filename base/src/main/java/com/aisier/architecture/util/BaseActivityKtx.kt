package com.aisier.architecture.util

import androidx.lifecycle.lifecycleScope
import com.aisier.architecture.base.BaseActivity
import com.aisier.network.entity.ApiResponse
import com.aisier.network.observer.ResultBuilder
import com.aisier.network.observer.parseData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> launchFlow(
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
): Flow<ApiResponse<T>> {
    return flow {
        emit(requestBlock())
    }.onStart {
        startCallback?.invoke()
    }.onCompletion {
        completeCallback?.invoke()
    }
}

/**
 * 这个方法只是简单的一个封装Loading的普通方法，不返回任何实体类
 */
fun BaseActivity.launchWithLoading(requestBlock: suspend () -> Unit) {
    lifecycleScope.launch {
        flow {
            emit(requestBlock())
        }.onStart {
            showLoading()
        }.onCompletion {
            dismissLoading()
        }.collect()
    }
}

/**
 * 请求不带Loading&&不需要声明LiveData
 */
fun <T> BaseActivity.launchAndCollect(requestBlock: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchFlow(requestBlock).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
}

/**
 * 请求带Loading&&不需要声明LiveData
 */
fun <T> BaseActivity.launchWithLoadingAndCollect(requestBlock: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchFlow(requestBlock, { showLoading() }, { dismissLoading() }).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
}



