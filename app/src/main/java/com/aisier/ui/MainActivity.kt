package com.aisier.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.startActivity
import com.aisier.architecture.util.toast
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.ActivityMainBinding
import com.aisier.util.TimerShareLiveData
import com.aisier.vm.MainViewModel
import com.aisier.vm.ShareViewModel

/**
 * 这种封装方式不支持Loading状态，需要自己手动书写Loading
 */
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mBinding by viewBinding(ActivityMainBinding::bind)
    private val mViewModel by viewModels<MainViewModel>()

    override fun init() {
        initData()
        initObserver()
        initGlobalObserver()
    }

    private fun initGlobalObserver() {
        getAppViewModelProvider().get(ShareViewModel::class.java).msgLiveData.observe(this) {
            toast("我是第二个页面的消息")
        }

        TimerShareLiveData.get().observe(this) {
            Log.i(TAG, "TimerShareLiveData value: $it")
        }
    }

    private fun initObserver() {
        mViewModel.wxArticleLiveData.observeState(this) {
            onSuccess { data: List<WxArticleBean>? ->
                Log.i(TAG, "$data: ")
                showNetErrorPic(false)
                mBinding.tvContent.text = data?.toString()
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
                Log.i(TAG, "onComplete: ")
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
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, MainFragment()).commit()
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
        mBinding.goSecondActivity.setOnClickListener { startActivity<SecondActivity>() }

        mBinding.goSaveStateActivity.setOnClickListener { startActivity<SavedStateActivity>() }

        mBinding.btLogin.setOnClickListener {
            showLoading()
            mViewModel.login("FastJetpack", "FastJetpack")
        }
    }

    companion object {
        private const val TAG = "MainActivity-->"
    }
}
