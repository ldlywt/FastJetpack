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

}

enum class DataState {
    STATE_CREATE,//创建
    STATE_LOADING,//加载中
    STATE_SUCCESS,//成功
    STATE_COMPLETED,//完成
    STATE_EMPTY,//数据为null
    STATE_FAILED,//接口请求成功但是服务器返回error
    STATE_ERROR,//请求失败
    STATE_UNKNOWN//未知
}