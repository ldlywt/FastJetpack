package com.aisier.architecture.ktx

import android.os.Handler
import android.os.Looper

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun ensureBackgroundThread(callback: () -> Unit) {
    if (isOnMainThread()) {
        Thread {
            callback()
        }.start()
    } else {
        callback()
    }
}

fun <T> T.mainThread(delayMillis: Long = 0, block: T.() -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        block()
    }, delayMillis)
}