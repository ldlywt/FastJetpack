package com.aisier.network

import com.jeremyliao.liveeventbus.LiveEventBus

const val SHOW_TOAST = "show_toast"

/**
 * 为了不引入AppContext，临时方案，不推荐这么用
 */
@Deprecated("临时方案，不推荐这么用")
fun toast(msg: String) {
    LiveEventBus.get<String>(SHOW_TOAST).post(msg)
}