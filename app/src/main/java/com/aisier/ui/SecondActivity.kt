package com.aisier.ui

import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivitySecondBinding
import com.aisier.util.TimerShareLiveData
import com.aisier.vm.ShareViewModel

class SecondActivity : BaseActivity(R.layout.activity_second) {

    private val mBinding by viewBinding(ActivitySecondBinding::bind)

    override fun init() {
        val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)

        mBinding.btSendMsg.setOnClickListener {
            shareViewModel.msgLiveData.postValue("给MainActivity发消息")
        }

        mBinding.btGetUserInfo.setOnClickListener {
            TimerShareLiveData.get().cancelTimer()
        }

        shareViewModel.msgLiveData.observe(this) {
            toast(it)
        }

        TimerShareLiveData.get().observe(this) {
            Log.i("wutao--> ", "SecondActivity: $it")
        }
    }
}