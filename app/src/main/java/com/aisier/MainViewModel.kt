package com.aisier

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aisier.architecture.base.BaseUiModel
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.entity.ResState
import com.aisier.architecture.entity.handlingExceptions
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WrapperTestBean
import com.aisier.bean.WxArticleBean
import com.aisier.net.ApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class MainViewModel : BaseViewModel() {

    private val repository by lazy { ApiRepository() }

    val resultUiLiveData = MutableLiveData<BaseUiModel<List<WrapperTestBean>>>()

    val wxArticleLiveData = StateLiveData<List<WxArticleBean>>()

    fun requestNet() {
        resultUiLiveData.postValue(BaseUiModel(showLoading = true))
        viewModelScope.launch {
            delay(1000)
            runCatching {
                repository.fetchWxArticle()
            }.onSuccess { states ->
                if (states is ResState.Success) {
                    handleData(states.data)
                } else if (states is ResState.Error) {
                    resultUiLiveData.postValue(BaseUiModel(showLoading = false, showError = states.exception.message))
                }
            }.onFailure { e ->
                resultUiLiveData.postValue(BaseUiModel(showLoading = false))
                handlingExceptions(e)
            }
        }
    }

    fun requestNetError() {
        resultUiLiveData.postValue(BaseUiModel(showLoading = true))
        viewModelScope.launch {
            delay(1000)
            runCatching {
                repository.fetchWxArticleError()
            }.onSuccess { states ->
                if (states is ResState.Success) {
                    handleData(states.data)
                } else if (states is ResState.Error) {
                    resultUiLiveData.postValue(BaseUiModel(showLoading = false, showError = states.exception.message))
                }
            }.onFailure { e ->
                resultUiLiveData.postValue(BaseUiModel(showLoading = false))
                handlingExceptions(e)
            }
        }
    }

    fun requestNetV2() {
        viewModelScope.launch {
            repository.fetchWxArticleV2(wxArticleLiveData)
        }
    }

    private fun handleData(data: List<WxArticleBean>) {
        val list = mutableListOf<WrapperTestBean>()
        data.forEach { list.add(WrapperTestBean(it)) }
        resultUiLiveData.postValue(BaseUiModel(showLoading = false, successData = list))
        Log.i("wutao--> ", "$list: ")
    }

    fun requestNetErrorV2() {
        viewModelScope.launch {
            repository.fetchWxArticleErrorV2(wxArticleLiveData)
        }
    }
}