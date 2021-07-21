package com.aisier.architecture.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.IUiView
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

    fun postLoading(baseResp: IBaseResponse<T>) {
        baseResp.dataState = DataState.STATE_LOADING
        postValue(baseResp)
    }

    fun setError(baseResp: IBaseResponse<T>, error: Throwable) {
        baseResp.dataState = DataState.STATE_ERROR
        baseResp.error = error
    }

    fun observeState(owner: LifecycleOwner, isShowLoading: IUiView? = null, listenerBuilder: ListenerBuilder.() -> Unit) {
        val listener = ListenerBuilder().also(listenerBuilder)
        val value = object : IStateObserver<T>(isShowLoading) {
            override fun onShowLoading() {
                if (listener.mShowLoadingListenerAction != null) {
                    listener.mShowLoadingListenerAction?.invoke()
                } else {
                    super.onShowLoading()
                }
            }

            override fun onDismissLoading() {
                if (listener.mDismissLoadingListenerAction != null) {
                    listener.mDismissLoadingListenerAction?.invoke()
                } else {
                    super.onDismissLoading()
                }

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

        }
        super.observe(owner, value)
    }

    inner class ListenerBuilder {
        internal var mSuccessListenerAction: ((T?) -> Unit)? = null
        internal var mShowLoadingListenerAction: (() -> Unit)? = null
        internal var mDismissLoadingListenerAction: (() -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable?) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null

        fun onSuccess(action: (T?) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onShowLoading(action: () -> Unit) {
            mShowLoadingListenerAction = action
        }

        fun onDismissLoading(action: () -> Unit) {
            mDismissLoadingListenerAction = action
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