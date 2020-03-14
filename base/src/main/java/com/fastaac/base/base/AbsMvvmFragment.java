package com.fastaac.base.base;

import android.os.Bundle;
import android.view.View;

import com.fastaac.base.util.TUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewbinding.ViewBinding;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public abstract class AbsMvvmFragment<T extends AbsViewModel, B extends ViewBinding> extends BaseFragment {

    protected T mViewModel;
    protected B mBinding;

    public AbsMvvmFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        mBinding = initBinding(view);
    }

    @Override
    public void onDestroyView() {
        mBinding = null;
        super.onDestroyView();
    }

    public abstract B initBinding(View view);

    protected <T extends ViewModel> T VMProviders(BaseFragment fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);
    }

}
