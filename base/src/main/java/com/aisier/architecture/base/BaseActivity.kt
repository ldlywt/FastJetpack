package com.aisier.architecture.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aisier.architecture.util.createActivityViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.core.LoadService

/**
 * <pre>
 * author : wutao
 * e-mail : ldlywt@163.com
 * time   : 2021/5/18
 * desc   :
 * version: 1.2
</pre> *
 */
abstract class BaseActivity<VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    AppCompatActivity(contentLayoutId) {

    private lateinit var loadService: LoadService<Any>

    protected val mViewModel: VM by lazy { this.createActivityViewModel() }

    private val mFactory: ViewModelProvider.Factory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        init()
    }

    protected abstract fun init()

    protected open fun getAppViewModelProvider(): ViewModelProvider = ViewModelProvider(BaseApp.instance, mFactory)

    protected fun setStatusBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .init()
    }

    protected fun setStatusBarDark() =
        ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init()


    private var progressDialog: ProgressDialog? = null
    protected fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    protected fun dismissLoading() {
        progressDialog?.dismiss()
    }

}