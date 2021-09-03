package com.aisier.architecture.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.aisier.architecture.util.toast
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * <pre>
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2021/6/18
 * desc   : 去掉类上面的泛型，因为反射会影响性能。并且优先选择组合而不是继承。
 * version: 1.3
</pre> *
 */
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId), IUiView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observeToast()
    }

    private fun observeToast() {
        LiveEventBus.get<String>(SHOW_TOAST).observe(this) {
            toast(it)
        }
    }

    protected abstract fun init()

    private var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

    companion object {
        const val SHOW_TOAST = "show_toast"
    }
}