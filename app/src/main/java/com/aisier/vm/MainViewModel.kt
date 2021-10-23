package com.aisier.vm

import androidx.lifecycle.viewModelScope
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import kotlinx.coroutines.launch

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class MainViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    val wxArticleLiveData = StateLiveData<List<WxArticleBean>>()
    val wxArticleLoadingLiveData = StateLiveData<List<WxArticleBean>>()

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
}