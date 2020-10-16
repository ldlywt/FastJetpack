package com.aisier.architecture.pagestate

import com.aisier.architecture.R
import com.kingja.loadsir.callback.Callback

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}