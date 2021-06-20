package com.aisier.architecture.entity

import com.aisier.architecture.util.toast

fun <T> handlingHttpResponse(res: HttpResponse,
                             successBlock: (data: T) -> Unit,
                             failureBlock: ((errorCode: Int, errorMsg: String?) -> Unit)? =
                                 { errorCode, errorMsg ->
                                     handlingApiExceptions(errorCode, errorMsg)
                                 }) {
    when (res) {
        is Success<*> -> successBlock.invoke(res.data as T)
        is Failure -> {
            with(res) {
                failureBlock?.invoke(errorCode, errorMsg) ?: toast(errorMsg ?: "$errorCode")
            }
        }
    }
}

fun <T : Any> ApiResponse<T>.convertHttpRes(): HttpResponse {
    return if (this.errorCode == 0) {
        data?.let {
            Success(it)
        } ?: Success(Any())
    } else {
        Failure(this.errorCode, this.errorMsg)
    }
}