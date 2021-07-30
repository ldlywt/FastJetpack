package com.aisier.ui

import android.Manifest
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivitySecondBinding
import com.aisier.util.TakePhotoLiveData
import com.aisier.util.TimerShareLiveData
import com.aisier.vm.ShareViewModel

class SecondActivity : BaseActivity(R.layout.activity_second) {

    private val mBinding by viewBinding(ActivitySecondBinding::bind)

    private lateinit var takePhotoLiveData: TakePhotoLiveData

    private val requestPermission: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result: Boolean ->
            toast("request permission $result")
        }

    override fun init() {
        val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)

        mBinding.btSendMsg.setOnClickListener {
            shareViewModel.msgLiveData.postValue("给MainActivity发消息")
        }

        mBinding.btGetUserInfo.setOnClickListener {
            TimerShareLiveData.get().cancelTimer()
        }

        takePhotoLiveData = TakePhotoLiveData(activityResultRegistry)
        takePhotoLiveData.observeForever { bitmap ->
            mBinding.imageView.setImageBitmap(bitmap)
        }

        mBinding.btTakePhoto.setOnClickListener {
            takePhotoLiveData.takePhotoLauncher.launch(null)
        }

        shareViewModel.msgLiveData.observe(this) {
            toast(it)
        }

        TimerShareLiveData.get().observe(this) {
            Log.i(TAG, "SecondActivity: $it")
        }

        mBinding.btRequestPermission.setOnClickListener {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        private const val TAG = "SecondActivity-->"
    }
}