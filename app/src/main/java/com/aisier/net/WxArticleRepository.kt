package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.entity.BaseResponse
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticle(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData) {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleError(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData) {
            mService.getWxArticleError()
        }
    }

    suspend fun fetchFromDb(mediatorLiveData: StateLiveData<List<WxArticleBean>>) {
        val data = getWxArticleFromDatabase()
        mediatorLiveData.value = data
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