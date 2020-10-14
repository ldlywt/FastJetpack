package com.fastaac.base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.fastaac.base.*
import com.fastaac.base.manager.NetState
import com.fastaac.base.manager.NetworkStateManager
import com.fastaac.base.pagestate.EmptyCallback
import com.fastaac.base.pagestate.ErrorCallback
import com.fastaac.base.pagestate.LoadingCallback
import com.fastaac.base.pagestate.TimeoutCallback
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
abstract class BaseActivity<VM : BaseViewModel, B : ViewBinding> : AppCompatActivity() {

    protected val context: Context
        get() = this

    protected val activity: Activity
        get() = this

    private lateinit var loadService: LoadService<Any>
    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B

    open fun showLoading() = loadService.showCallback(LoadingCallback::class.java)

    open fun dismissLoading() = loadService.showCallback(SuccessCallback::class.java)

    open fun handleError() = loadService.showCallback(ErrorCallback::class.java)

    protected abstract fun initBinding(): B

    protected abstract fun viewModelClass(): Class<VM>

    protected abstract fun init()

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T =
            defaultViewModelProviderFactory.create(modelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        lifecycle.addObserver(NetworkStateManager)
        NetworkStateManager.networkStateCallback.observe(this, Observer(this::onNetworkStateChanged))
        mBinding = initBinding()
        mViewModel = getActivityViewModel(viewModelClass())
        setContentView(mBinding.root)
        init()
        initViewModelAction()
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

    private fun initViewModelAction() {
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