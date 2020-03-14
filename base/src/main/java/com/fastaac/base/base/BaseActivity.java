package com.fastaac.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.fastaac.base.pagestate.EmptyCallback;
import com.fastaac.base.pagestate.ErrorCallback;
import com.fastaac.base.pagestate.LoadingCallback;
import com.gyf.immersionbar.ImmersionBar;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/12/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerPageState();
        LiveEventBus.get().with(AbsViewModel.PAGE_STATE, BaseResult.class).observe(this,
                httpResult -> loadService.showWithConvertor(httpResult));
    }

    protected void setStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init();
    }

    protected void setStatusBarDark() {
        ImmersionBar.with(this).statusBarDarkFont(true).transparentBar().init();
    }

    private void registerPageState() {
        loadService = LoadSir.getDefault().register(this, (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            retryClick();
        }, (Convertor<BaseResult>) httpResult -> {
            Class<? extends Callback> resultCode;
            switch (httpResult.getErrorCode()) {
                case BaseResult.SUCCESS_CODE:
                    if (httpResult.getData() != null) {
                        resultCode = SuccessCallback.class;
                    } else {
                        resultCode = EmptyCallback.class;
                    }
                    break;
                default:
                    resultCode = ErrorCallback.class;
            }
            return resultCode;
        });
    }


    protected void retryClick() {
        ToastUtils.showShort("重新请求");
    }

    /**
     * 获取一个 Context 对象
     */
    public Context getContext() {
        return getBaseContext();
    }


    /**
     * 获取当前 Activity 对象
     */
    public <A extends BaseActivity> A getActivity() {
        return (A) this;
    }

    /**
     * 跳转到其他 Activity 并销毁当前 Activity
     */
    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivity(cls);
        finish();
    }

    /**
     * 跳转到其他 Activity
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

}
