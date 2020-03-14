package com.fastaac.base.base;

import android.os.Bundle;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public interface IUiCallback {
    //返回布局文件id
//    int getLayoutId();
    //初始化数据
    void initData(Bundle savedInstanceState);
    //初始化布局文件
    void initView();
}
