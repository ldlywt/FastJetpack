package com.fastaac.base.base

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.fastaac.base.pagestate.*
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
open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        Utils.init(this)
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

    companion object {
        var app: BaseApp? = null
            private set
    }
}