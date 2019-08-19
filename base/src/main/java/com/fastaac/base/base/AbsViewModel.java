package com.fastaac.base.base;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public abstract class AbsViewModel extends AndroidViewModel {

    public static final String PAGE_STATE = "PAGE_STATE";

    public AbsViewModel(@NonNull Application application) {
        super(application);
    }

    protected void postPageState(BaseResult baseResult) {
        LiveEventBus.get().with(PAGE_STATE).post(baseResult);
    }

}
