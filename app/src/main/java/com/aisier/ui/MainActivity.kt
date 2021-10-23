package com.aisier.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.startActivity
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivityMainBinding
import com.aisier.livedata.NetworkWatchLiveData
import com.aisier.livedata.TimerGlobalLiveData

/**
 * 这种封装方式不支持Loading状态，需要自己手动书写Loading
 */
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mBinding by viewBinding(ActivityMainBinding::bind)

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                toast(activityResult.data?.getStringExtra("key") ?: "")
            }
        }

    override fun init() {
        initData()
        initGlobalObserver()
    }

    private fun initGlobalObserver() {

        TimerGlobalLiveData.get().observe(this) {
            Log.i(TAG, "GlobalTimer value: ==  $it")
        }

        NetworkWatchLiveData.get().observe(this) {
            Log.i("wutao--> ", "network change: $it")
        }
    }

    private fun initData() {
        mBinding.goSecondActivity.setOnClickListener {
            activityResultLauncher.launch(Intent(this, SecondActivity::class.java))
        }

        mBinding.goSaveStateActivity.setOnClickListener { startActivity<SavedStateActivity>() }

        mBinding.btApiActivity.setOnClickListener { startActivity<ApiActivity>() }

        mBinding.btStartTimer.setOnClickListener { TimerGlobalLiveData.get().startTimer() }

    }

    companion object {
        private const val TAG = "MainActivity-->"
    }
}
