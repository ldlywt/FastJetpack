package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse

abstract class IStateObserver<T> : Observer<IBaseResponse<T>> {

    override fun onChanged(t: IBaseResponse<T>) {
        when (t.dataState) {
            DataState.STATE_LOADING -> onShowLoading()
            DataState.STATE_SUCCESS -> {
                onDismissLoading()
                onSuccess(t.httpData)
            }

            DataState.STATE_EMPTY -> {
                onDismissLoading()
                onDataEmpty()
            }

            DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                onDismissLoading()
                t.error?.let { onError(it) }
            }
            else -> onDismissLoading()
        }
        onComplete()

    }

    abstract fun onSuccess(data: T?)

    abstract fun onDataEmpty()

    abstract fun onShowLoading()

    abstract fun onDismissLoading()

    abstract fun onError(e: Throwable?)

    abstract fun onComplete()

}