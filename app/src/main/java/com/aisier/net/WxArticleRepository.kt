package com.aisier.net

import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.network.base.BaseRepository
import com.aisier.network.entity.ApiResponse

class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return executeHttp {
            mService.login(username, password)
        }
    }

}