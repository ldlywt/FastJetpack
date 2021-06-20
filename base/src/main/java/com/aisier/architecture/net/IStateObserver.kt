package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.base.IUiView

abstract class IStateObserver<T>(private val uiView: IUiView? = null) : Observer<BaseResp<T>> {

    override fun onChanged(t: BaseResp<T>) {
        when (t.dataState) {
            DataState.STATE_LOADING -> onShowLoading()
            DataState.STATE_SUCCESS -> {
                onDismissLoading()
                onDataChange(t.data)
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

    abstract fun onDataChange(data: T?)

    open fun onDataEmpty() = Unit

    open fun onShowLoading() = uiView?.showLoading()

    open fun onDismissLoading() = uiView?.dismissLoading()


    open fun onError(e: Throwable?) = Unit

}