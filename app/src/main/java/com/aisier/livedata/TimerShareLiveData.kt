package com.aisier.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

class TimerShareLiveData : LiveData<Int>() {

    private val timerManager = TimerManager()

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
        fun get(): TimerShareLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else TimerShareLiveData()
            return sInstance
        }
    }

}