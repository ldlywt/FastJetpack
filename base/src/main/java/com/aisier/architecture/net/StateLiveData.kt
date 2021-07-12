package com.aisier.architecture.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse
import com.aisier.architecture.util.toast


class StateLiveData<T> : MutableLiveData<IBaseResponse<T>>() {

    private val stateListenerList = mutableListOf<ListenerBuilder?>()

    fun postLoading(baseResp: IBaseResponse<T>) {
        baseResp.dataState = DataState.STATE_LOADING
        postValue(baseResp)
    }

    fun setError(baseResp: IBaseResponse<T>, error: Throwable) {
        baseResp.dataState = DataState.STATE_ERROR
        baseResp.error = error
    }

    fun observeState(owner: LifecycleOwner, listenerBuilder: ListenerBuilder.() -> Unit) {
        stateListenerList.add(ListenerBuilder().also(listenerBuilder))
        val value = object : IStateObserver<T>() {
            override fun onShowLoading() {
                stateListenerList.forEach { listener ->
                    listener?.mShowLoadingListenerAction?.invoke()
                }
            }

            override fun onDismissLoading() {
                stateListenerList.forEach { listener ->
                    listener?.mDismissLoadingListenerAction?.invoke()
                }
            }

            override fun onSuccess(data: T?) {
                stateListenerList.forEach { listener ->
                    listener?.mSuccessListenerAction?.invoke(data)
                }
            }

            override fun onError(e: Throwable?) {
                stateListenerList.forEach { listener ->
                    listener?.mErrorListenerAction?.invoke(e) ?: toast("Http Error")
                }
            }

            override fun onDataEmpty() {
                stateListenerList.forEach { listener ->
                    listener?.mEmptyListenerAction?.invoke()
                }
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
    }

}