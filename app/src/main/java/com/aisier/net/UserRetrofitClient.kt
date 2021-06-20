package com.aisier.net

import com.aisier.architecture.net.BaseRetrofitClient
import okhttp3.OkHttpClient

object UserRetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}
