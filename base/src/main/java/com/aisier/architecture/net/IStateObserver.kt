package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse

abstract class IStateObserver<T> : Observer<IBaseResponse<T>> {

    override fun onChanged(t: IBaseResponse<T>) {
        when (t.dataState) {
            DataState.STATE_SUCCESS -> onSuccess(t.httpData)
            DataState.STATE_EMPTY -> onDataEmpty()
            DataState.STATE_FAILED -> onFailed(t.httpCode)
            DataState.STATE_ERROR -> t.error?.let { onError(it) }
        }
        onComplete()
    }

    abstract fun onSuccess(data: T?)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable?)

    abstract fun onComplete()

    abstract fun onFailed(httpCode: Int)

}