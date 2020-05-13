package com.fastaac.base.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.fastaac.base.*
import com.fastaac.base.manager.NetState
import com.fastaac.base.pagestate.EmptyCallback
import com.fastaac.base.pagestate.ErrorCallback
import com.fastaac.base.pagestate.LoadingCallback
import com.fastaac.base.pagestate.TimeoutCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseVMActivity<VM : AbsViewModel, B : ViewBinding> : BaseActivity() {
    private lateinit var loadService: LoadService<Any>
    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = initBinding()
        mViewModel = getActivityViewModel(viewModelClass())
        setContentView(mBinding.root)
        init()
        initViewModelAction()
    }

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
                EmptyState ->loadService.showCallback(EmptyCallback::class.java)
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

    override fun onNetworkStateChanged(netState: NetState?) {
        super.onNetworkStateChanged(netState)
        if (netState?.isSuccess != true) {
            loadService.showCallback(TimeoutCallback::class.java)
        } else {
            loadService.showCallback(SuccessCallback::class.java)
        }
    }

    open fun showLoading() {
        loadService.showCallback(LoadingCallback::class.java)
    }

    open fun dismissLoading() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun handleError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    protected abstract fun initBinding(): B

    protected abstract fun viewModelClass(): Class<VM>

    protected abstract fun init()

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        return defaultViewModelProviderFactory.create(modelClass)
    }
}