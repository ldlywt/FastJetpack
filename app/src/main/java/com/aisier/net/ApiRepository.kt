package com.aisier.net

import com.aisier.architecture.base.BaseRepository

class ApiRepository : BaseRepository() {

    suspend fun fetchWxArticle() = executeResp(RetrofitClient.service.getWxArticle())
}