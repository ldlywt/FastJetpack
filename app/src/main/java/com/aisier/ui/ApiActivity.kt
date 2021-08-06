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
                dismissLoading()
            }
        }

        mViewModel.mediatorLiveDataLiveData.observe(this) {
            showNetErrorPic(false)
            mBinding.tvContent.text = it.data.toString()
        }

        mViewModel.userLiveData.observeState(this) {
            onSuccess {
                mBinding.tvContent.text = it.toString()
            }

            onComplete {
                dismissLoading()
            }
        }
    }

    private fun showNetErrorPic(isShowError: Boolean) {
        mBinding.tvContent.isGone = isShowError
        mBinding.ivContent.isVisible = isShowError
    }

    private fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, ApiFragment()).commit()
        mBinding.btnNet.setOnClickListener {
            showLoading()
            mViewModel.requestNet()
        }
        mBinding.btnNetError1.setOnClickListener {
            showLoading()
            mViewModel.requestNetError()
        }
        mBinding.btnMultipleDataSourcesDb.setOnClickListener {
            mBinding.tvContent.text = ""
            mViewModel.requestFromDb()
        }
        mBinding.btnMultipleDataSourcesNet.setOnClickListener {
            mBinding.tvContent.text = ""
            mViewModel.requestFromNet()
        }
        mBinding.btLogin.setOnClickListener {
            showLoading()
            mViewModel.login("FastJetpack", "FastJetpack")
        }

    }


}