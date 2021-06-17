package com.aisier.activity

import android.util.Log
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
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

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mBinding by viewBinding(ActivityMainBinding::bind)
    private val mViewModel by viewModels<MainViewModel>()

    override fun init() {
        initData()
        getAppViewModelProvider().get(ShareViewModel::class.java).msgLiveData.observe(this) {
            toast("我是第二个页面的消息")
        }

        TimerShareLiveData.get(MainActivity::class.simpleName).observe(this) {
            Log.i("wutao--> ", "MainActivity: $it")
        }

        UserCacheLiveData.getCacheUserData().observe(this) {
            Log.i("wutao--> ", "MainActivity:User info $it")
        }
    }

    private fun initData() {
        mBinding.btnNet.setOnClickListener {
            mViewModel.requestNetV2()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, MainFragment()).commit()

        mBinding.goSecondActivity.setOnClickListener {
            go<SecondActivity>()
        }
        mViewModel.resultUiLiveData.observe(this) { uiState ->
            uiState.showLoading.let { if (it) showLoading() else dismissLoading() }
            uiState.showSuccess.let { mBinding.tvContent.text = it.toString() }
            uiState.showError?.let { toast(it) }
        }

    }
}
