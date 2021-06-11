package com.aisier.architecture.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aisier.architecture.*
import com.aisier.architecture.manager.NetState
import com.aisier.architecture.manager.NetworkStateManager
import com.aisier.architecture.pagestate.EmptyCallback
import com.aisier.architecture.pagestate.ErrorCallback
import com.aisier.architecture.pagestate.LoadingCallback
import com.aisier.architecture.pagestate.TimeoutCallback
import com.aisier.architecture.util.createActivityViewModel
import com.aisier.architecture.util.toast
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

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

    open fun showLoading() = loadService.showCallback(LoadingCallback::class.java)

    open fun dismissLoading() = loadService.showCallback(SuccessCallback::class.java)

    open fun handleError() = loadService.showCallback(ErrorCallback::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        init()
        initPageStates()
        initNetworkStateManager()
    }

    protected abstract fun init()

    protected open fun getAppViewModelProvider(): ViewModelProvider = ViewModelProvider(BaseApp.instance, mFactory)

    private fun initNetworkStateManager() {
        lifecycle.addObserver(NetworkStateManager)
        NetworkStateManager.networkStateCallback.observe(this, androidx.lifecycle.Observer(this::onNetworkStateChanged))
    }

    protected fun setStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init()
    }

    protected fun setStatusBarDark() = ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init()

    protected open fun retryClick() = toast("重新请求")

    override fun onStart() {
        super.onStart()
        registerPageState()
    }

    private fun registerPageState() {
        loadService = LoadSir.getDefault().register(this) {
            loadService.showCallback(LoadingCallback::class.java)
            retryClick()
        }
    }

    private fun initPageStates() {
        mViewModel.stateActionEvent.observe(this, { stateActionState ->
            when (stateActionState) {
                LoadState -> showLoading()
                EmptyState -> loadService.showCallback(EmptyCallback::class.java)
                SuccessState -> {
                    dismissLoading()
                    loadService.showSuccess()
                }
                is ToastState -> stateActionState.message?.let { toast(it) }
                is ErrorState -> {
                    dismissLoading()
                    stateActionState.message?.let {
                        toast(it)
                        handleError()
                    }
                }
            }
        })
    }

    protected fun onNetworkStateChanged(netState: NetState?) {
        if (netState?.isSuccess != true) {
            loadService.showCallback(TimeoutCallback::class.java)
        } else {
            loadService.showCallback(SuccessCallback::class.java)
        }
    }

}