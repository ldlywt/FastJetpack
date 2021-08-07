package com.aisier.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivitySecondBinding
import com.aisier.livedata.RequestPermissionLiveData
import com.aisier.livedata.TakePhotoLiveData
import com.aisier.livedata.TimerShareLiveData
import com.aisier.vm.ShareViewModel

class SecondActivity : BaseActivity(R.layout.activity_second) {

    private val mBinding by viewBinding(ActivitySecondBinding::bind)

    private var takePhotoLiveData: TakePhotoLiveData =
        TakePhotoLiveData(activityResultRegistry, "key")

    private var requestPermissionLiveData = RequestPermissionLiveData(activityResultRegistry, "key")

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result: Boolean ->
            toast("request permission $result")
        }

    override fun init() {
        val shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)

        mBinding.btSendMsg.setOnClickListener {
            shareViewModel.msgLiveData.postValue("给MainActivity发消息")
        }

        takePhotoLiveData.observe(this) { bitmap ->
            mBinding.imageView.setImageBitmap(bitmap)
        }

        mBinding.btTakePhoto.setOnClickListener {
            takePhotoLiveData.takePhoto()
        }

        TimerShareLiveData.get().observe(this) {
            Log.i(TAG, "SecondActivity: $it")
        }

        mBinding.btRequestPermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        mBinding.btRequestPermissionV2.setOnClickListener {
            requestPermissionLiveData.requestPermission(Manifest.permission.RECORD_AUDIO)
        }

        requestPermissionLiveData.observe(this) {
            toast("权限RECORD_AUDIO请求结果   $it")
        }

        mBinding.btBack.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra("key", "返回消息"))
            finish()
        }
    }

    companion object {
        private const val TAG = "SecondActivity-->"
    }
}