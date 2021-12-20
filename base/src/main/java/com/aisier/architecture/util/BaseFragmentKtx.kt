package com.aisier.architecture.util

import androidx.lifecycle.lifecycleScope
import com.aisier.architecture.base.BaseFragment
import com.aisier.network.ResultBuilder
import com.aisier.network.entity.*
import com.aisier.network.launchFlow
import com.aisier.network.parseResultAndCallback
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 这个方法只是简单的一个封装Loading的普通方法，不返回任何实体类
 */
fun BaseFragment.launchWithLoading(requestBlock: suspend () -> Unit) {
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
fun <T> BaseFragment.launchAndCollect(requestBlock: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchFlow(requestBlock).collect { response ->
            parseResultAndCallback(response, listenerBuilder)
        }
    }
}

/**
 * 请求带Loading&&不需要声明LiveData
 */
fun <T> BaseFragment.launchWithLoadingAndCollect(requestBlock: suspend () -> ApiResponse<T>, listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchFlow(requestBlock, { showLoading() }, { dismissLoading() }).collect { response ->
            parseResultAndCallback(response, listenerBuilder)
        }
    }
}