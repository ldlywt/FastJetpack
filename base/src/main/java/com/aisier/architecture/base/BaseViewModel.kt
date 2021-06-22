package com.aisier.architecture.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

open class BaseUiModel<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var successData: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新
)

inline fun <T> BaseUiModel<T>.onSuccess(action: (value: T?) -> Unit): BaseUiModel<T> {
    action(successData)
    return this
}

inline fun <T> BaseUiModel<T>.onError(action: (value: String?) -> Unit): BaseUiModel<T> {
    showError?.let {
        action(it)
    }
    return this
}

inline fun <T> BaseUiModel<T>.onLoading(action: (value: Boolean) -> Unit): BaseUiModel<T> {
    action(showLoading)
    return this
}