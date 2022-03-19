package com.aisier.architecture.base

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * author : wutao
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())
    }

    companion object {
        lateinit var instance: BaseApp
            private set
    }

    private inner class ApplicationLifecycleObserver : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            // TODO: "ApplicationObserver: app moved to foreground"
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            // TODO: "ApplicationObserver: app moved to background"
        }
    }
}