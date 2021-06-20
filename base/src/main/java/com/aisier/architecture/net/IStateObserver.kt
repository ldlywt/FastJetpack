package com.aisier.architecture.net

import android.util.Log
import androidx.lifecycle.Observer

private const val TAG = "IStateObserver"

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：LiveData Observer的一个类，
 * 主要结合LoadSir，根据BaseResp里面的State分别加载不同的UI，如Loading，Error
 * 同时重写onChanged回调，分为onDataChange，onDataEmpty，onError，
 * 开发者可以在UI层，每个接口请求时，直接创建IStateObserver，重写相应callback。
 */
abstract class IStateObserver<T> : Observer<BaseResp<T>> {

    override fun onChanged(t: BaseResp<T>) {
        Log.d(TAG, "onChanged: ${t.dataState}")

        when (t.dataState) {
            DataState.STATE_LOADING -> {
                onLoading(true)
            }
            DataState.STATE_SUCCESS -> {
                //请求成功，数据不为null
                onDataChange(t.data)
            }

            DataState.STATE_EMPTY -> {
                //数据为空
                onDataEmpty()
            }

            DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                //请求错误
                t.error?.let { onError(it) }
            }
            else -> {
            }
        }
    }

    /**
     * 请求数据且数据不为空
     */
    open abstract fun onDataChange(data: T?)

    /**
     * 请求成功，但数据为空
     */
    open fun onDataEmpty() {

    }

    /**
     * 请求成功，但数据为空
     */
    open fun onLoading(isShow: Boolean) {

    }

    /**
     * 请求错误
     */
    open fun onError(e: Throwable?) {

    }

}