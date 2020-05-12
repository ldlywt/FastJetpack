package com.fastaac.base.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fastaac.base.LoadState
import com.fastaac.base.StateActionEvent

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class AbsViewModel(application: Application) : AndroidViewModel(application) {
    val stateActionEvent = MutableLiveData<StateActionEvent>()

    fun showLoading(){
        stateActionEvent.postValue(LoadState)
    }
}