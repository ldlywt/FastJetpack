package com.aisier.architecture.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.entity.ApiResponse

/**
 * <pre>
 * @author : wutao
 * time   : 2021/07/01
 * desc   :
 * version: 1.0
</pre> *
 */
class StateLiveData<T> : MutableLiveData<ApiResponse<T>>() {

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

            override fun onSuccess(data: T) {
                listener.mSuccessListenerAction?.invoke(data)
            }

            override fun onError(e: Throwable) {
                listener.mErrorListenerAction?.invoke(e)
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
        internal var mSuccessListenerAction: ((T) -> Unit)? = null
        internal var mShowLoadingListenerAction: (() -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null
        internal var mFailedListenerAction: ((Int) -> Unit)? = null

        fun onSuccess(action: (T) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onShowLoading(action: () -> Unit) {
            mShowLoadingListenerAction = action
        }

        fun onException(action: (Throwable) -> Unit) {
            mErrorListenerAction = action
        }

        fun onEmpty(action: () -> Unit) {
            mEmptyListenerAction = action
        }

        fun onComplete(action: () -> Unit) {
            mCompleteListenerAction = action
        }

        fun onFailed(action: (Int) -> Unit) {
            mFailedListenerAction = action
        }
    }

}