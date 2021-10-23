package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.entity.*

abstract class IStateObserver<T>(private val uiView: IUiView? = null) : Observer<ApiResponse<T>> {

    override fun onChanged(t: ApiResponse<T>) {
        if (t is ApiLoadingResponse) {
            onShowLoading()
            return
        }
        when (t) {
            is ApiSuccessResponse -> onSuccess(t.response)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(t.errorCode!!)
            is ApiErrorResponse -> onError(t.error!!)
        }
        uiView?.dismissLoading()
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onDataEmpty()

    open fun onShowLoading() = uiView?.showLoading()

    abstract fun onError(e: Throwable)

    abstract fun onComplete()

    abstract fun onFailed(httpCode: Int)

}