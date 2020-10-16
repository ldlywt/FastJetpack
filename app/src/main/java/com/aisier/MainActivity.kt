package com.aisier

import androidx.lifecycle.observe
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.go
import com.aisier.databinding.ActivityMainBinding
import com.blankj.utilcode.util.ToastUtils

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun init() {
        initData()
        getAppViewModelProvider().get(ShareViewModel::class.java).msgLiveData.observe(this){
            ToastUtils.showShort("我是第二个页面的消息")
        }

    }

    private fun initData() {
        mBinding.btnNet.setOnClickListener {
            Thread { mViewModel.requestNet() }.start()
        }
        mBinding.btnNoNet.setOnClickListener { mViewModel.clickNoNet() }
        mBinding.btnEmpty.setOnClickListener { mViewModel.clickNoData() }
        mViewModel.resultLiveData.observe(this) {
            mBinding.tvContent.text = it.data?.toString()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, MainFragment()).commit()

        mBinding.goSecondActivity.setOnClickListener {
            go<SecondActivity>()
        }
    }

    override fun retryClick() {
        super.retryClick()
        Thread { mViewModel.requestNet() }.start()
    }
}
