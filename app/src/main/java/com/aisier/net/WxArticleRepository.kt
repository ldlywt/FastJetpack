package com.aisier.net

import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.network.base.BaseRepository
import com.aisier.network.entity.ApiResponse
import com.aisier.network.entity.ApiSuccessResponse
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

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return executeHttp {
            mService.login(username, password)
        }
    }

    private suspend fun getWxArticleFromDatabase(): ApiResponse<List<WxArticleBean>> = withContext(Dispatchers.IO) {
        val bean = WxArticleBean()
        bean.id = 999
        bean.name = "零先生"
        bean.visible = 1
        ApiSuccessResponse(arrayListOf(bean))
    }


}