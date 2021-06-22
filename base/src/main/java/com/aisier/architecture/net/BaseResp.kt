package com.aisier.architecture.net

class BaseResp<T> {
    var errorCode = -1
    var errorMsg: String? = null
    val data: T? = null
    var dataState: DataState? = null
    var error: Throwable? = null
    val isSuccess: Boolean
        get() = errorCode == 0
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