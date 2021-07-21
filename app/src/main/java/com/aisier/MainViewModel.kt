package com.aisier

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.entity.IBaseResponse
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
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

    private val repository by lazy { WxArticleRepository() }

    val wxArticleLiveData = StateLiveData<List<WxArticleBean>>()
    val wxArticleLoadingLiveData = StateLiveData<List<WxArticleBean>>()
    private val dbLiveData = StateLiveData<List<WxArticleBean>>()
    private val apiLiveData = StateLiveData<List<WxArticleBean>>()
    val mediatorLiveDataLiveData = MediatorLiveData<IBaseResponse<List<WxArticleBean>>>().apply {
        this.addSource(apiLiveData) {
            this.value = it
        }
        this.addSource(dbLiveData) {
            this.value = it
        }
    }

    fun requestNet() {
        wxArticleLiveData.postLoading()
        viewModelScope.launch {
            wxArticleLiveData.value = repository.fetchWxArticle()
        }
    }

    fun requestNetError() {
        wxArticleLiveData.postLoading()
        viewModelScope.launch {
            wxArticleLiveData.value = repository.fetchWxArticleError()
        }
    }

    fun requestNetWithLoading() {
        viewModelScope.launch {
            wxArticleLoadingLiveData.value = repository.fetchWxArticle()
        }
    }

    fun requestFromNet() {
        viewModelScope.launch {
            apiLiveData.value = repository.fetchWxArticle()
        }
    }

    fun requestFromDb() {
        viewModelScope.launch {
            dbLiveData.value = repository.fetchFromDb()
        }
    }
}