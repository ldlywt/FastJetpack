package com.aisier.architecture.util

import androidx.lifecycle.lifecycleScope
import com.aisier.architecture.base.BaseActivity
import com.aisier.network.entity.*
import com.aisier.network.observer.ResultBuilder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun BaseActivity.launchWithLoading(block: suspend () -> Unit) {
    lifecycleScope.launch {
        flow {
            emit(block())
        }.onStart {
            showLoading()
        }.onCompletion {
            dismissLoading()
        }.collect()
    }
}

fun <T> BaseActivity.launchWithLoadingGetFlow(block: suspend () -> ApiResponse<T>): Flow<ApiResponse<T>> {
    return flow {
        emit(block())
    }.onStart {
        showLoading()
    }.onCompletion {
        dismissLoading()
    }
}

/**
 * 请求不带Loading&&不需要声明LiveData
 */
fun <T> BaseActivity.launchAndCollect(block: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        flow {
            emit(block())
        }.collect { response ->
            parseResultAndCallback(response, listenerBuilder)
        }
    }
}

/**
 * 请求带Loading&&不需要声明LiveData
 */
fun <T> BaseActivity.launchWithLoadingAndCollect(block: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchWithLoadingGetFlow(block).collect { response ->
            parseResultAndCallback(response, listenerBuilder)
        }
    }
}

private fun <T> parseResultAndCallback(response: ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (response) {
        is ApiSuccessResponse -> listener.onSuccess(response.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(response.errorCode, response.errorMsg)
        is ApiErrorResponse -> listener.onError(response.throwable)
    }
    listener.onComplete()
}
