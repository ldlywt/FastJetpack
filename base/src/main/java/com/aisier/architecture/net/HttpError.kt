package com.aisier.architecture.net

import com.aisier.architecture.util.toast
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

enum class HttpError(var code: Int, var errorMsg: String) {
    TOKEN_EXPIRE(3001, "token is expired"),
    PARAMS_ERROR(4003, "params is error")
    // ...... more
}

fun handlingApiExceptions(code: Int?, errorMsg: String?) = when (code) {
    HttpError.TOKEN_EXPIRE.code -> toast(HttpError.TOKEN_EXPIRE.errorMsg)
    HttpError.PARAMS_ERROR.code -> toast(HttpError.PARAMS_ERROR.errorMsg)
    else -> errorMsg?.let { toast(it) }
}

/**
 * 处理请求层的错误,对可能的已知的错误进行处理
 */
fun handlingExceptions(e: Throwable) {
    return when (e) {
        is HttpException -> {
        }
        is CancellationException -> {
        }
        is SocketTimeoutException -> {
        }
        is JsonParseException -> {
        }
        else -> {
        }
    }
}
