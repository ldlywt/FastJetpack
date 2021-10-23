package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean

class WxArticleRepository : BaseRepository() {

    private val mService by lazy { RetrofitClient.service }

    suspend fun fetchWxArticle(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData, mService::getWxArticle)
    }

    suspend fun fetchWxArticleError(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData, mService::getWxArticleError)
    }

}