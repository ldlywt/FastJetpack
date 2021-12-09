package com.aisier.ui

import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.base.BaseToolBarActivity
import com.aisier.architecture.util.*
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.ActivityApiBinding
import com.aisier.network.observer.observeState
import com.aisier.network.toast
import com.aisier.vm.ApiViewModel

class ApiActivity : BaseToolBarActivity(R.layout.activity_api) {

    private val mBinding by viewBinding(ActivityApiBinding::bind)
    private val mViewModel by viewModels<ApiViewModel>()

    override fun init() {
        initData()
        initObserver()
    }

    private fun initObserver() {
        mViewModel.wxArticleLiveData.observeState(this) {

            onSuccess = { data: List<WxArticleBean>? ->
                showNetErrorPic(false)
                mBinding.tvContent.text = data.toString()
            }

            onDataEmpty = { }

            onComplete = this@ApiActivity::dismissLoading

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

    /**
     * 不接收返回结果，在viewmodel中通过livedata发送
     */
    private fun requestNet() {
        launchWithLoading {
            mViewModel.requestNet()
        }
    }

    private fun requestNetError() {
        launchWithLoading {
            mViewModel.requestNetError()
        }
    }

    /**
     * 链式调用，返回结果的处理都在一起，viewmodel中不需要创建一个livedata对象
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
        val loginLiveData = launchFlow(requestBlock = { mViewModel.login("FastJetpack", "FastJetpack11") }).asLiveData()

        loginLiveData.observeState(this) {
            onSuccess = {
                mBinding.tvContent.text = it.toString()
            }
            onFailed = { errorCode, errorMsg ->
                toast("errorCode: $errorCode   errorMsg: $errorMsg")
            }
        }
    }


}