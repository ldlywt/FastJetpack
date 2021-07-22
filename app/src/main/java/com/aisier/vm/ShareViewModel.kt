package com.aisier.vm

import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.BaseViewModel

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/10/14
 * desc   : Application级别的ViewModel，全局通用
 * version: 1.0
 */
class ShareViewModel : BaseViewModel() {
    val msgLiveData = MutableLiveData<String>()
}