package com.aisier

import androidx.lifecycle.observe
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.base.EmptyViewModel
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity<EmptyViewModel, ActivitySecondBinding>() {
    override fun initBinding(): ActivitySecondBinding {
        return ActivitySecondBinding.inflate(layoutInflater)
    }

    override fun init() {
        val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)
        shareViewModel.msgLiveData.observe(this){
            toast(it)
        }

        mBinding.btSendMsg.setOnClickListener {
            shareViewModel.msgLiveData.postValue("给MainActivity发消息")
        }

    }

}