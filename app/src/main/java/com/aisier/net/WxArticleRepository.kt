package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.entity.ApiResponse
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleFromDb(): ApiResponse<List<WxArticleBean>> {
        return getWxArticleFromDatabase()
    }

    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }

    suspend fun login(username: String, password: String): ApiResponse<User> {
        return executeHttp {
            mService.login(username, password)
        }
    }

    private suspend fun getWxArticleFromDatabase(): ApiResponse<List<WxArticleBean>> = withContext(Dispatchers.IO) {
        val bean = WxArticleBean()
        bean.id = 999
        bean.name = "零先生"
        bean.visible = 1
        val response = ApiResponse<List<WxArticleBean>>()
        val list = arrayListOf(bean)
        response.data = list
        response
    }


}