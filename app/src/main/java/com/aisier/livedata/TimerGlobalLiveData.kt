package com.aisier.livedata

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

class TimerGlobalLiveData : LiveData<Int>() {

    private val handler: Handler = Handler(Looper.getMainLooper())

    private val timerRunnable = object : Runnable {
        override fun run() {
            postValue(count++)
            handler.postDelayed(this, 1000)
        }
    }

    fun startTimer() {
        count = 0
        handler.postDelayed(timerRunnable, 1000)
    }

    fun cancelTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    companion object {
        private lateinit var sInstance: TimerGlobalLiveData

        private var count = 0

        @MainThread
        fun get(): TimerGlobalLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else TimerGlobalLiveData()
            return sInstance
        }
    }

}