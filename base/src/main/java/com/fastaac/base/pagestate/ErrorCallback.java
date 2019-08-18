package com.fastaac.base.pagestate;


import com.kingja.loadsir.callback.Callback;
import com.fastaac.base.R;


/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
