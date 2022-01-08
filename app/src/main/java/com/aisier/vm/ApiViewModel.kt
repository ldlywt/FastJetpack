package com.aisier.vm

import com.aisier.architecture.base.BaseViewModel
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import com.aisier.network.entity.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    // 使用StateFlow 替代livedata
//    val wxArticleLiveData = StateMutableLiveData<List<WxArticleBean>>()

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()

    suspend fun requestNet() {
        _uiState.value = repository.fetchWxArticleFromNet()
    }

    suspend fun requestNetError() {
        _uiState.value = repository.fetchWxArticleError()
    }

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return repository.login(username, password)
    }
}