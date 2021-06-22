package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse

abstract class IStateObserver<T>(private val uiView: IUiView? = null) : Observer<IBaseResponse<T>> {

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
    }

    abstract fun onSuccess(data: T?)

    open fun onDataEmpty() = Unit

    open fun onShowLoading() = uiView?.showLoading()

    open fun onDismissLoading() = uiView?.dismissLoading()

    open fun onError(e: Throwable?) = Unit

}