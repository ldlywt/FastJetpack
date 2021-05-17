package com.aisier.util

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

class TimerShareLiveData(tag: String? = null) : LiveData<Int>() {

    private val timerManager = TimerManager(tag)

    private val listener: TimeIntervalCallback = object : TimeIntervalCallback {
        override fun secondListener(number: Int) {
            value = number
        }
    }

    override fun onActive() = timerManager.registerTimeIntervalCallback(listener)

    override fun onInactive() = timerManager.unRegisterTimeIntervalCallback()

    fun cancelTimer(){
        timerManager.cancelTimer()
    }

    companion object {
        private lateinit var sInstance: TimerShareLiveData

        @MainThread
        fun get(tag: String? = null): TimerShareLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else TimerShareLiveData(tag)
            return sInstance
        }
    }

}