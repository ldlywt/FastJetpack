package com.aisier.ui

import android.widget.Button
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.base.BaseToolBarActivity
import com.aisier.architecture.util.startActivity
import com.aisier.architecture.util.toast
import com.aisier.databinding.ActivityMainBinding

class MainActivity : BaseToolBarActivity(R.layout.activity_main) {

    override fun init() {

        findViewById<Button>(R.id.bt_api_activity).setOnClickListener { startActivity<ApiActivity>() }

        findViewById<Button>(R.id.go_save_state_activity).setOnClickListener { startActivity<SavedStateActivity>() }
    }

    override fun isShowBackArrow(): Boolean  = false

}
