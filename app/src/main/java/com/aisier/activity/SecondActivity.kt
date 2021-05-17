package com.aisier.activity

import android.util.Log
import androidx.lifecycle.observe
import com.aisier.ShareViewModel
import com.aisier.util.UserCacheLiveData
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.base.EmptyViewModel
import com.aisier.architecture.util.toast
import com.aisier.bean.UserBean
import com.aisier.databinding.ActivitySecondBinding
import com.aisier.util.TimerShareLiveData

class SecondActivity : BaseActivity<EmptyViewModel, ActivitySecondBinding>() {
    override fun initBinding(): ActivitySecondBinding {
        return ActivitySecondBinding.inflate(layoutInflater)
    }

    override fun init() {
        val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)
        shareViewModel.msgLiveData.observe(this){
            toast(it)
        }

        setOnclick(shareViewModel)

        TimerShareLiveData.get(MainActivity::class.simpleName).observe(this) {
            Log.i("wutao--> ", "SecondActivity: $it")
        }

    }

    private fun setOnclick(shareViewModel: ShareViewModel) {
        mBinding.btSendMsg.setOnClickListener {
            shareViewModel.msgLiveData.postValue("给MainActivity发消息")
        }
        mBinding.btGetUserInfo.setOnClickListener {
            TimerShareLiveData.get().cancelTimer()
            UserCacheLiveData.cacheUser(UserBean("张三",(Math.random() * 1000).toLong()))
        }
    }

}