package com.aisier.architecture.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {

    open class BaseUiModel<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var successData: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新
    )

    /**
     * @param tryBlock 尝试执行的挂起代码块
     * @param catchBlock 捕获异常的代码块 "协程对Retrofit的实现在失败、异常时没有onFailure的回调而是直接已Throwable的形式抛出"
     * @param finallyBlock finally代码块
     */
    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 在主线程中开启
     * catchBlock、finallyBlock 并不是必须,不同的业务对于错误的处理也可能不同想要完全统一的处理是很牵强的
     */
    fun launchOnMain(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {},             // 默认空实现，可根据具体情况变化
        finallyBlock: suspend CoroutineScope.() -> Unit = {}) {
        viewModelScope.launch {
            tryCatch(tryBlock, catchBlock, finallyBlock)
        }
    }

    /**
     * 在IO线程中开启,修改为Dispatchers.IO
     */
    fun launchOnIO(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            tryCatch(tryBlock, catchBlock, finallyBlock)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}