package com.aisier.architecture.base

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.aisier.architecture.pagestate.*
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
open class BaseApp : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore


    override fun onCreate() {
        super.onCreate()
        baseApp = this
        mAppViewModelStore = ViewModelStore()
        initPageState()
        initLiveBus()
    }

    private fun initPageState() {
        LoadSir.beginBuilder()
                .addCallback(ErrorCallback())
                .addCallback(EmptyCallback())
                .addCallback(LoadingCallback())
                .addCallback(TimeoutCallback())
                .addCallback(PlaceholderCallback())
                .setDefaultCallback(SuccessCallback::class.java)
                .commit()
    }

    private fun initLiveBus() {
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true)
    }

    override fun getViewModelStore(): ViewModelStore = mAppViewModelStore

    companion object {
        lateinit var baseApp: BaseApp
            private set
    }
}