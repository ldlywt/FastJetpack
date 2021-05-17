package com.aisier.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisier.bean.UserBean

object UserCacheLiveData {

    private val userCacheLiveData = MutableLiveData<UserBean>()

    fun cacheUser(userBean: UserBean) {
        userCacheLiveData.value = userBean
    }

    fun getCacheUserData(): LiveData<UserBean> {
        return userCacheLiveData
    }
}