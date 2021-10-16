package com.aisier.architecture.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisier.network.entity.ApiResponse
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

    protected fun <T> launchWithLoading(
        requestBlock: suspend () -> ApiResponse<T>,
        resultCallback: (ApiResponse<T>) -> Unit
    ) {
        viewModelScope.launch {
            flow {
                emit(requestBlock.invoke())
            }.onStart {
                showLoading()
            }.onCompletion {
                stopLoading()
            }.collect {
                resultCallback(it)
            }
        }
    }

}