package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean

class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticle() = executeResp(mService.getWxArticle())

    suspend fun fetchWxArticleError() = executeResp(mService.getWxArticleError())

    suspend fun fetchWxArticleV2(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp({ mService.getWxArticleV2() }, stateLiveData)
    }

    suspend fun fetchWxArticleErrorV2(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp({ mService.getWxArticleErrorV2() }, stateLiveData)
    }

}