package com.aisier.net

import com.aisier.architecture.base.BaseRepository
import com.aisier.architecture.net.StateLiveData
import com.aisier.bean.WxArticleBean

class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleV2(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData, mService::getWxArticleV2)
    }

    suspend fun fetchWxArticleErrorV2(stateLiveData: StateLiveData<List<WxArticleBean>>) {
        executeResp(stateLiveData, mService::getWxArticleErrorV2)
    }

}