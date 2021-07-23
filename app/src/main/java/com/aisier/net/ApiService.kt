package com.aisier.net

import com.aisier.architecture.entity.BaseResponse
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): BaseResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): BaseResponse<List<WxArticleBean>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): BaseResponse<User>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}