package com.aisier.architecture.pagestate

import android.content.Context
import android.view.View
import com.aisier.architecture.R
import com.kingja.loadsir.callback.Callback

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
class PlaceholderCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_placeholder
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        return true
    }
}