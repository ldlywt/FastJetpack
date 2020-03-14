package com.fastaac.base.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    protected boolean mIsFirstVisible = true;

    public BaseFragment() {
    }

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }


    protected void retryClick() {
        ToastUtils.showShort("重新请求");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.mActivity = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    private void needLazy() {
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    /**
     * 数据懒加载
     */
    protected void lazyLoad() {

    }

    /**
     * 跳转到其他 Activity 并销毁当前 Activity
     *
     * @param cls 目标Activity的Class
     */
    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivity(cls);
        mActivity.finish();
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls 目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getContext(), cls));
    }

}
