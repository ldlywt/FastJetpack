package com.aisier.vm

import com.aisier.architecture.base.BaseViewModel
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import com.aisier.network.entity.ApiResponse
import com.aisier.network.observer.StateMutableLiveData

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class ApiViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    val wxArticleLiveData = StateMutableLiveData<List<WxArticleBean>>()

    suspend fun requestNet() {
        wxArticleLiveData.value = repository.fetchWxArticleFromNet()
    }

    suspend fun requestNetError() {
        wxArticleLiveData.value = repository.fetchWxArticleError()
    }

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return repository.login(username, password)
    }
}