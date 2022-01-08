package com.aisier.architecture.base

import androidx.lifecycle.ViewModel

/**
 * author : wutao
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel()

sealed class ViewEffect {
    data class ShowLoading(val isShow: Boolean) : ViewEffect()
    data class ShowToast(val message: String) : ViewEffect()
    data class Success<T>(val data: T) : ViewEffect()
    object Empty : ViewEffect()
}