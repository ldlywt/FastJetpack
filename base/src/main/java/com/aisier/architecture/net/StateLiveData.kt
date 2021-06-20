package com.aisier.architecture.net

import androidx.lifecycle.MutableLiveData


/**
 * @instruction：MutableLiveData,用于将请求状态分发给UI
 */
class StateLiveData<T> : MutableLiveData<BaseResp<T>>() {
}