package com.fastaac.base.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
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