package com.aisier.vm

import androidx.lifecycle.viewModelScope
import com.aisier.architecture.base.BaseViewModel
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import com.aisier.network.observer.StateLiveData
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
class ApiViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    val wxArticleLiveData = StateLiveData<List<WxArticleBean>>()
    val userLiveData = StateLiveData<User?>()

    fun login(username: String, password: String) {
        launchWithLoading(requestBlock = {
            repository.login(username, password)
        }, resultCallback = {
            userLiveData.value = it
        })
    }

    fun requestNetWithFlow() {
        launchWithLoading(requestBlock = {
            repository.fetchWxArticleFromNet()
        }, resultCallback = { response ->
            wxArticleLiveData.value = response
        })
    }

    fun requestNetError() {
        viewModelScope.launch {
            wxArticleLiveData.value = repository.fetchWxArticleError()
        }
    }
}