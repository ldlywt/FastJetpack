package com.aisier.architecture.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.aisier.architecture.*
import com.aisier.architecture.manager.NetState
import com.aisier.architecture.manager.NetworkStateManager
import com.aisier.architecture.pagestate.EmptyCallback
import com.aisier.architecture.pagestate.ErrorCallback
import com.aisier.architecture.pagestate.LoadingCallback
import com.aisier.architecture.pagestate.TimeoutCallback
import com.aisier.architecture.util.GenericUtil
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * <pre>
 * author : wutao
 * e-mail : ldlywt@163.com
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
</pre> *
 */
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected val context: Context
        get() = this

    protected val activity: Activity
        get() = this

    private lateinit var loadService: LoadService<Any>
    protected  val mViewModel: VM by lazy {
        getActivityViewModel<VM>(GenericUtil.getGeneric(this, 0))
    }
    protected lateinit var mBinding: VB
        private set

    open fun showLoading() = loadService.showCallback(LoadingCallback::class.java)

    open fun dismissLoading() = loadService.showCallback(SuccessCallback::class.java)

    open fun handleError() = loadService.showCallback(ErrorCallback::class.java)

    protected abstract fun initBinding(): VB

    protected abstract fun init()

    protected open fun <VM : ViewModel> getActivityViewModel(modelClass: Class<VM>): VM =
            defaultViewModelProviderFactory.create(modelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        mBinding = initBinding()
        setContentView(mBinding.root)
        init()
        initPageStates()
        initNetworkStateManager()
    }

    private fun initNetworkStateManager() {
        lifecycle.addObserver(NetworkStateManager)
        NetworkStateManager.networkStateCallback.observe(this, Observer(this::onNetworkStateChanged))
    }

    protected fun setStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init()
    }

    protected fun setStatusBarDark() =
            ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init()

    protected open fun retryClick() = ToastUtils.showShort("重新请求")


    override fun onStart() {
        super.onStart()
        registerPageState()
    }

    protected fun registerPageState() {
        loadService = LoadSir.getDefault().register(this) {
            loadService.showCallback(LoadingCallback::class.java)
            retryClick()
        }
    }

    private fun initPageStates() {
        mViewModel.stateActionEvent.observe(this, Observer { stateActionState ->
            when (stateActionState) {
                LoadState -> showLoading()
                EmptyState -> loadService.showCallback(EmptyCallback::class.java)
                SuccessState -> {
                    dismissLoading()
                    loadService.showSuccess()
                }
                is ToastState -> ToastUtils.showShort(stateActionState.message)
                is ErrorState -> {
                    dismissLoading()
                    stateActionState.message?.apply {
                        ToastUtils.showShort(this)
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