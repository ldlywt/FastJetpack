package com.aisier.util

import android.os.Handler
import android.os.Looper

interface TimeIntervalCallback {
    fun secondListener(number: Int)
}

class TimerManager {

    private var timeIntervalCallback: TimeIntervalCallback? = null

    private val handler: Handler = Handler(Looper.getMainLooper())

    init {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // 模拟网络请求
                timeIntervalCallback?.secondListener(++count)
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    fun registerTimeIntervalCallback(listener: TimeIntervalCallback) {
        timeIntervalCallback = listener
    }

    fun unRegisterTimeIntervalCallback() {
        timeIntervalCallback = null
    }

    fun cancelTimer() {
        unRegisterTimeIntervalCallback()
        count = 0
    }

    companion object {
        private var count = 0
    }
}