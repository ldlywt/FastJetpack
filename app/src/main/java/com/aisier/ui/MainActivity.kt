package com.aisier.ui

import com.aisier.R
import com.aisier.architecture.base.BaseToolBarActivity

class MainActivity : BaseToolBarActivity(R.layout.activity_home) {

    override fun init() = Unit

    override fun isShowBackArrow(): Boolean = false

}
