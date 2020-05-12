package com.fastaac.base.pagestate

import com.fastaac.base.R
import com.kingja.loadsir.callback.Callback

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}