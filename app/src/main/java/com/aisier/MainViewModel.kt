package com.aisier

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.BaseResult
import com.aisier.architecture.base.BaseViewModel
import com.aisier.bean.TestBean
import com.aisier.bean.WrapperTestBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    private val url = "https://wanandroid.com/wxarticle/chapters/json"
    val resultUiLiveData = MutableLiveData<BaseUiModel<List<WrapperTestBean>>>()
    private val client = OkHttpClient()

    fun requestNetV2() {
        launchOnUI {
            val result = fetchData() as String
            val jsonData = parseJson(result)
            val list = mutableListOf<WrapperTestBean>()
            jsonData?.data?.forEach { list.add(WrapperTestBean(it)) }
            resultUiLiveData.postValue(BaseUiModel(showLoading = false, showSuccess = list))
        }
    }

    private fun parseJson(result: String): BaseResult<List<TestBean>>? {
        return Gson().fromJson(
            result,
            object : TypeToken<BaseResult<List<TestBean>>>() {}.type
        )
    }

    private suspend fun fetchData() = withContext(Dispatchers.IO) {
        try {
            resultUiLiveData.postValue(BaseUiModel(showLoading = true))
            delay(1000)
            run(url)
        } catch (e: IOException) {
            resultUiLiveData.postValue(BaseUiModel(showLoading = false, showError = e.toString()))
            e.printStackTrace()
        }
    }


    @Throws(IOException::class)
    private fun run(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }

}