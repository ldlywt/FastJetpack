package com.fastaac.base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastaac.base.util.TUtil;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public abstract class AbsMvvmFragment<T extends AbsViewModel, B extends ViewDataBinding> extends BaseFragment {

    protected T mViewModel;
    protected B mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        return mBinding.getRoot();
    }

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(BaseFragment fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);
    }

}
