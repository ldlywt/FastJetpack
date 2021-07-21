package com.aisier.architecture.base

import android.app.Application
import androidx.lifecycle.*
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
open class BaseApp : Application(), ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLiveBus()
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())
    }

    private fun initLiveBus() {
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true)
    }

    override fun getViewModelStore(): ViewModelStore = ViewModelStore()

    companion object {
        lateinit var instance: BaseApp
            private set
    }

    private inner class ApplicationLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        private fun onAppForeground() {
            // TODO: "ApplicationObserver: app moved to foreground"
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        private fun onAppBackground() {
            // TODO: "ApplicationObserver: app moved to background"
        }
    }
}