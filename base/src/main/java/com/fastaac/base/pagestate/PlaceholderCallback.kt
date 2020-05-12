package com.fastaac.base.pagestate

import android.content.Context
import android.view.View
import com.fastaac.base.R
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