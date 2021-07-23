package com.aisier.architecture.entity

import java.io.Serializable

interface IBaseResponse<Data> : Serializable {

    val httpCode: Int

    val httpMsg: String?

    val httpData: Data?

    val isSuccess: Boolean

    var dataState: DataState?

    var error: Throwable?

    val isFailed: Boolean
        get() = !isSuccess

    var testData: Data?
}

enum class DataState {
    STATE_SUCCESS,
    STATE_EMPTY,
    STATE_FAILED,
    STATE_ERROR,
}