package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.entity.*

abstract class IStateObserver<T> : Observer<ApiResponse<T>> {

    override fun onChanged(response: ApiResponse<T>) {
        when (response) {
            is ApiSuccessResponse -> onSuccess(response.data)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(response.errorCode)
            is ApiErrorResponse -> response.error?.let { onError(it) }
        }
        onComplete()
    }

    abstract fun onSuccess(data: T?)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable?)

    abstract fun onComplete()

    abstract fun onFailed(httpCode: Int?)

}