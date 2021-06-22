package com.aisier.net

import com.aisier.architecture.entity.ApiResponse
import com.aisier.architecture.net.BaseResp
import com.aisier.bean.WxArticleBean
import retrofit2.http.GET

interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleV2(): BaseResp<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleErrorV2(): BaseResp<List<WxArticleBean>>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}