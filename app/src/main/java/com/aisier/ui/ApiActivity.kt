package com.aisier.ui

import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.ActivityApiBinding
import com.aisier.vm.ApiViewModel

class ApiActivity : BaseActivity(R.layout.activity_api) {

    private val mBinding by viewBinding(ActivityApiBinding::bind)
    private val mViewModel by viewModels<ApiViewModel>()

    override fun init() {
        initData()
        initObserver()
    }

    private fun initObserver() {
        // 也可以使用类似EventBus，可以没必要每个Activity都写这个代码
//        mViewModel.loadingLiveData.observe(this) {
//            if (it) showLoading() else dismissLoading()
//        }
        mViewModel.wxArticleLiveData.observeState(this) {
            onSuccess { data: List<WxArticleBean> ->
                showNetErrorPic(false)
                mBinding.tvContent.text = data.toString()
            }

            onFailed { code, msg ->
            }

            onException {
                showNetErrorPic(true)
            }

            onEmpty {
            }

            onComplete {
            }
        }

        mViewModel.userLiveData.observeState(this) {
            onSuccess {
                mBinding.tvContent.text = it.toString()
            }
        }
    }

    private fun showNetErrorPic(isShowError: Boolean) {
        mBinding.tvContent.isGone = isShowError
        mBinding.ivContent.isVisible = isShowError
    }

    private fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, ApiFragment()).commit()

        mBinding.btnNetError.setOnClickListener {
            mViewModel.requestNetError()
        }
        mBinding.btLogin.setOnClickListener {
            mViewModel.login("FastJetpack", "FastJetpack")
        }

        mBinding.btnNetFlow.setOnClickListener {
            mViewModel.requestNetWithFlow()
        }

    }


}