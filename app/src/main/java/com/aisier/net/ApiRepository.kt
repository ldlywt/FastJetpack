package com.aisier.net

class ApiRepository {

    suspend fun fetchWxArticle() = UserRetrofitClient.service.getWxArticle()
}