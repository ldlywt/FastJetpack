package com.aisier.net

import com.aisier.architecture.entity.ApiResponse
import com.aisier.bean.WxArticleBean
import retrofit2.http.GET

interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}