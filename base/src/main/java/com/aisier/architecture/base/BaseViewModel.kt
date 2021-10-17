package com.aisier.architecture.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisier.network.LOADING_STATE
import com.aisier.network.entity.ApiResponse
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()

    protected fun showLoading() {
        loadingLiveData.postValue(true)
    }

    protected fun stopLoading() {
        loadingLiveData.postValue(false)
    }

    protected fun showLoadingV2() {
        LiveEventBus.get<Boolean>(LOADING_STATE).post(true)
    }

    protected fun stopLoadingV2() {
        LiveEventBus.get<Boolean>(LOADING_STATE).post(false)
    }

    protected fun <T> launchWithLoading(
        requestBlock: suspend () -> ApiResponse<T>,
        resultCallback: (ApiResponse<T>) -> Unit
    ) {
        viewModelScope.launch {
            flow {
                emit(requestBlock.invoke())
            }.onStart {
                showLoadingV2()
            }.onCompletion {
                stopLoadingV2()
            }.collect {
                resultCallback(it)
            }
        }
    }

}