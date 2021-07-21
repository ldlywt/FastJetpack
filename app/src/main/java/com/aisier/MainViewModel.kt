package com.aisier

import androidx.lifecycle.viewModelScope
import com.aisier.architecture.base.BaseViewModel
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

    fun requestNet() {
        viewModelScope.launch {
            repository.fetchWxArticleV2(wxArticleLiveData)
        }
    }

    fun requestNetError() {
        viewModelScope.launch {
            repository.fetchWxArticleErrorV2(wxArticleLiveData)
        }
    }

    fun requestNetWithLoading() {
        viewModelScope.launch {
            repository.fetchWxArticleV2(wxArticleLoadingLiveData)
        }
    }
}