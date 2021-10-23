package com.aisier.architecture.base

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
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {

/*  使用消息事件总线，不需要这个，减少代码
    val loadingLiveData = MutableLiveData<Boolean>()

    protected fun showLoading() {
        loadingLiveData.postValue(true)
    }

    protected fun stopLoading() {
        loadingLiveData.postValue(false)
    }
    */

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