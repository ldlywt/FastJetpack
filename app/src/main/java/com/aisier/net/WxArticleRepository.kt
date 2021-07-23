package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.entity.BaseResponse
import com.aisier.architecture.entity.IBaseResponse
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): IBaseResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleFromDb(): IBaseResponse<List<WxArticleBean>> {
        return getWxArticleFromDatabase()
    }

    suspend fun fetchWxArticleError(): IBaseResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }

    suspend fun login(username: String, password: String): IBaseResponse<User> {
        return executeHttp {
            mService.login(username, password)
        }
    }

    private suspend fun getWxArticleFromDatabase(): BaseResponse<List<WxArticleBean>> = withContext(Dispatchers.IO) {
        val bean = WxArticleBean()
        bean.id = 999
        bean.name = "零先生"
        bean.visible = 1
        val response = BaseResponse<List<WxArticleBean>>()
        val list = arrayListOf(bean)
        response.testData = list
        response
    }


}