package com.aisier.activity

import android.util.Log
import androidx.lifecycle.observe
import com.aisier.MainFragment
import com.aisier.MainViewModel
import com.aisier.R
import com.aisier.ShareViewModel
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.go
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivityMainBinding
import com.aisier.util.TimerShareLiveData
import com.aisier.util.UserCacheLiveData

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun init() {
        initData()
        getAppViewModelProvider().get(ShareViewModel::class.java).msgLiveData.observe(this) {
            toast("我是第二个页面的消息")
        }

        TimerShareLiveData.get(MainActivity::class.simpleName).observe(this) {
            Log.i("wutao--> ", "MainActivity: $it")
        }

        UserCacheLiveData.getCacheUserData().observe(this){
            Log.i("wutao--> ", "MainActivity:User info $it")
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
