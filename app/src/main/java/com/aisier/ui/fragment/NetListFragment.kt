package com.aisier.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseFragment
import com.aisier.architecture.util.launchFlow
import com.aisier.architecture.util.launchWithLoading
import com.aisier.architecture.util.launchWithLoadingAndCollect
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.FragmentNetListBinding
import com.aisier.network.observer.launchAndCollectIn
import com.aisier.network.observer.observeState
import com.aisier.network.toast
import com.aisier.vm.ApiViewModel

/**
 * dev 分支去掉LiveData，使用Flow
 */
class NetListFragment : BaseFragment(R.layout.fragment_net_list) {

    private val mViewModel by activityViewModels<ApiViewModel>()
    private val mBinding: FragmentNetListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initObserver()
    }

    private fun initObserver() {
        mViewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = { result: List<WxArticleBean>? ->
                mBinding.tvContent.text = result.toString()
            }

            onComplete = { }

            onFailed = { code, msg ->
                toast("errorCode: $code   errorMsg: $msg")
            }

            onError = {
                showNetErrorPic(true)
            }
        }
    }

    private fun showNetErrorPic(isShowError: Boolean) {
        mBinding.tvContent.isGone = isShowError
        mBinding.ivContent.isVisible = isShowError
    }

    private fun initData() {
        mBinding.btnNet.setOnClickListener {
            showNetErrorPic(false)
            requestNet()
        }

        mBinding.btnNetError.setOnClickListener {
            showNetErrorPic(false)
            requestNetError()
        }

        mBinding.btLogin.setOnClickListener {
            showNetErrorPic(false)
            login()
        }
    }

    private fun requestNet() {
        launchWithLoading(mViewModel::requestNet)
    }

    private fun requestNetError() {
        launchWithLoading(mViewModel::requestNetError)
    }

    /**
     * 链式调用，返回结果的处理都在一起，viewmodel中不需要创建一个livedata对象
     * 适用于不需要监听数据变化的场景
     */
    private fun login() {
        launchWithLoadingAndCollect({
            mViewModel.login("FastJetpack", "FastJetpack")
        }) {
            onSuccess = {
                mBinding.tvContent.text = it.toString()
            }
            onFailed = { errorCode, errorMsg ->
                toast("errorCode: $errorCode   errorMsg: $errorMsg")
            }
        }
    }

    /**
     * 将Flow转变为LiveData
     */
    private fun loginAsLiveData() {
        val loginLiveData =
            launchFlow(requestBlock = { mViewModel.login("FastJetpack", "FastJetpack11") }).asLiveData()

        loginLiveData.observeState(this) {
            onSuccess = { mBinding.tvContent.text = it.toString() }
            onFailed =
                { errorCode, errorMsg -> toast("errorCode: $errorCode   errorMsg: $errorMsg") }
        }
    }
}