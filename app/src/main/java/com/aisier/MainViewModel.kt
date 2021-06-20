package com.aisier

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.entity.ResState
import com.aisier.architecture.entity.handlingExceptions
import com.aisier.bean.WrapperTestBean
import com.aisier.bean.WxArticleBean
import com.aisier.net.ApiRepository
import kotlinx.coroutines.delay

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class MainViewModel : BaseViewModel() {

    private val repository by lazy { ApiRepository() }

    val resultUiLiveData = MutableLiveData<BaseUiModel<List<WrapperTestBean>>>()

    fun requestNet() {
        resultUiLiveData.postValue(BaseUiModel(showLoading = true))
        launchOnIO(
                tryBlock = {
                    delay(1000)
                    val states: ResState<List<WxArticleBean>> = repository.fetchWxArticle()
                    if (states is ResState.Success) {
                        handleData(states.data)
                    } else if (states is ResState.Error) {
                        resultUiLiveData.postValue(BaseUiModel(showLoading = false, showError = states.exception.message))
                    }
                },
                catchBlock = { e ->
                    handlingExceptions(e)
                }

        )
    }

    private fun handleData(data: List<WxArticleBean>) {
        val list = mutableListOf<WrapperTestBean>()
        data.forEach { list.add(WrapperTestBean(it)) }
        resultUiLiveData.postValue(BaseUiModel(showLoading = false, successData = list))
        Log.i("wutao--> ", "$list: ")
    }
}