package com.aisier

import androidx.lifecycle.observe
import com.aisier.architecture.base.BaseActivity
import com.aisier.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun init() {
        initData()
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
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, TestFragment()).commit()
    }

    override fun retryClick() {
        super.retryClick()
        Thread { mViewModel.requestNet() }.start()
    }
}
