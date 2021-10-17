package com.aisier.network

import com.jeremyliao.liveeventbus.LiveEventBus

const val SHOW_TOAST = "show_toast"

const val LOADING_STATE = "loading_state"

fun toast(msg: String) {
    LiveEventBus.get<String>(SHOW_TOAST).post(msg)
}