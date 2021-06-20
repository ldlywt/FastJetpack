package com.aisier.architecture.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar

/**
 * <pre>
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2021/6/18
 * desc   : 去掉类上面的泛型，因为反射会影响性能。并且优先选择组合而不是继承。
 * version: 1.3
</pre> *
 */
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) :
    AppCompatActivity(contentLayoutId), IUiView {

    private val mFactory: ViewModelProvider.Factory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        init()
    }

    protected abstract fun init()

    protected open fun getAppViewModelProvider(): ViewModelProvider =
        ViewModelProvider(BaseApp.instance, mFactory)

    protected fun setStatusBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .init()
    }

    protected fun setStatusBarDark() =
        ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init()


    private var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

}