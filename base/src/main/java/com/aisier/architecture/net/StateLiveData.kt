package com.aisier.architecture.net

import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse


class StateLiveData<T> : MutableLiveData<IBaseResponse<T>>() {

    fun postLoading(baseResp: IBaseResponse<T>) {
        baseResp.dataState = DataState.STATE_LOADING
        postValue(baseResp)
    }

    fun setError(baseResp: IBaseResponse<T>, error: Throwable) {
        baseResp.dataState = DataState.STATE_ERROR
        baseResp.error = error
    }

}