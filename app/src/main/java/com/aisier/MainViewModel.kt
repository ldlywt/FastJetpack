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
    val mediatorLiveDataLiveData = MediatorLiveData<IBaseResponse<List<WxArticleBean>>>()
    val dbLiveData = StateLiveData<List<WxArticleBean>>()

    init {
        mediatorLiveDataLiveData.addSource(wxArticleLoadingLiveData) {
            mediatorLiveDataLiveData.value = it
        }
        mediatorLiveDataLiveData.addSource(dbLiveData) {
            mediatorLiveDataLiveData.value = it
        }
    }

    fun requestNet() {
        viewModelScope.launch {
            repository.fetchWxArticle(wxArticleLiveData)
        }
    }

    fun requestNetError() {
        viewModelScope.launch {
            repository.fetchWxArticleError(wxArticleLiveData)
        }
    }

    fun requestNetWithLoading() {
        viewModelScope.launch {
            repository.fetchWxArticle(wxArticleLoadingLiveData)
        }
    }

    fun requestFromDb() {
        viewModelScope.launch {
            repository.fetchFromDb(dbLiveData)
        }
    }
}