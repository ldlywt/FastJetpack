package com.fastaac.base.base;

import android.os.Bundle;

import com.fastaac.base.util.TUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
public abstract class BaseMvvMActivity<T extends AbsViewModel, B extends ViewBinding> extends BaseActivity {

    protected T mViewModel;
    protected B mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = VMProviders(TUtil.getInstance(this, 0));
        mBinding = initBinding();
        setContentView(mBinding.getRoot());
    }

    public abstract B initBinding();

    protected <T extends ViewModel> T VMProviders(@NonNull Class modelClass) {
        return (T) getDefaultViewModelProviderFactory().create(modelClass);
    }

}
