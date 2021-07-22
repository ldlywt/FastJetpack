package com.aisier.architecture.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.entity.BaseResponse
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse
import com.aisier.architecture.util.toast

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2021/07/01
 * desc   :
 * version: 1.0
</pre> *
 */
class StateLiveData<T> : MutableLiveData<IBaseResponse<T>>() {

    fun postLoading() {
        val baseResp: IBaseResponse<T> = BaseResponse()
        baseResp.dataState = DataState.STATE_LOADING
        postValue(baseResp)
    }

    fun observeState(owner: LifecycleOwner, listenerBuilder: ListenerBuilder.() -> Unit) {
        val listener = ListenerBuilder().also(listenerBuilder)
        val value = object : IStateObserver<T>() {
            override fun onShowLoading() {
                listener.mShowLoadingListenerAction?.invoke()
            }

            override fun onSuccess(data: T?) {
                listener.mSuccessListenerAction?.invoke(data)
            }

            override fun onError(e: Throwable?) {
                listener.mErrorListenerAction?.invoke(e) ?: toast("Http Error")
            }

            override fun onDataEmpty() {
                listener.mEmptyListenerAction?.invoke()
            }

            override fun onComplete() {
                listener.mCompleteListenerAction?.invoke()
            }

            override fun onFailed(httpCode: Int) {
                listener.mFailedListenerAction?.invoke(httpCode)
            }

        }
        super.observe(owner, value)
    }

    inner class ListenerBuilder {
        internal var mSuccessListenerAction: ((T?) -> Unit)? = null
        internal var mShowLoadingListenerAction: (() -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable?) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null
        internal var mFailedListenerAction: ((Int) -> Unit)? = null

        fun onShowLoading(action: () -> Unit) {
            mShowLoadingListenerAction = action
        }

        fun onSuccess(action: (T?) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onFailed(action: (Int) -> Unit) {
            mFailedListenerAction = action
        }

        fun onException(action: (Throwable?) -> Unit) {
            mErrorListenerAction = action
        }

        fun onEmpty(action: () -> Unit) {
            mEmptyListenerAction = action
        }

        fun onComplete(action: () -> Unit) {
            mCompleteListenerAction = action
        }
    }

}