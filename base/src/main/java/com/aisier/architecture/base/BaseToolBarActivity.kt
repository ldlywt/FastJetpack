package com.aisier.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.aisier.architecture.R

abstract class BaseToolBarActivity(@LayoutRes val layoutResId: Int) : BaseActivity(R.layout.activity_base_toolbar) {

    protected var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbarView()
        LayoutInflater.from(this).inflate(layoutResId, findViewById<FrameLayout>(R.id.container))
        init()
    }

    protected abstract fun init()

    private fun initToolbarView() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        showBackArrow(isShowBackArrow())
        toolbar?.setNavigationOnClickListener { finish() }
    }

    private fun showBackArrow(isShow: Boolean) {
        supportActionBar?.setHomeButtonEnabled(isShow)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
    }

    protected open fun isShowBackArrow(): Boolean = true
}