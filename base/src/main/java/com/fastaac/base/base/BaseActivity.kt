package com.fastaac.base.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.fastaac.base.manager.NetState
import com.fastaac.base.manager.NetworkStateManager
import com.gyf.immersionbar.ImmersionBar

/**
 * <pre>
 * author : wutao
 * e-mail : ldlywt@163.com
 * time   : 2018/12/07
 * desc   :
 * version: 1.0
</pre> *
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        lifecycle.addObserver(NetworkStateManager)
        NetworkStateManager.networkStateCallback.observe(this, Observer(this::onNetworkStateChanged))
    }

    protected open fun onNetworkStateChanged(netState: NetState?) {
        //TODO 子类可以重写该方法，统一的网络状态通知和处理
    }

    protected fun setStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init()
    }

    protected fun setStatusBarDark() {
        ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init()
    }

    protected open fun retryClick() {
        ToastUtils.showShort("重新请求")
    }

    val context: Context
        get() = baseContext
}