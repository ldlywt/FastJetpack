package com.aisier.ui

import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.startActivity
import com.aisier.databinding.ActivityMainBinding

/**
 * 这种封装方式不支持Loading状态，需要自己手动书写Loading
 */
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mBinding by viewBinding(ActivityMainBinding::bind)

    override fun init() {
        mBinding.goSaveStateActivity.setOnClickListener { startActivity<SavedStateActivity>() }

        mBinding.btApiActivity.setOnClickListener { startActivity<ApiActivity>() }
    }

}
