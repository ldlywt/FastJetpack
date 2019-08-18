package com.fastaac.base;

import android.app.Application;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.fastaac.base.pagestate.EmptyCallback;
import com.fastaac.base.pagestate.ErrorCallback;
import com.fastaac.base.pagestate.LoadingCallback;
import com.fastaac.base.pagestate.TimeoutCallback;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public class BaseApp extends Application {

    private static BaseApp sApp = null;

    public static BaseApp getApp() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(SuccessCallback.class)//设置默认状态页
                .commit();
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true);
    }
}
