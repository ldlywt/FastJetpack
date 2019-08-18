package com.fastaac.base.base;

import android.view.LayoutInflater;

import com.fastaac.base.util.TUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
public abstract class AbsMvvmActivity<T extends AbsViewModel, B extends ViewDataBinding> extends BaseActivity {

    protected T mViewModel;
    protected B mBinding;

    public AbsMvvmActivity() {

    }

    @Override
    public void setContentView(int layoutResID) {
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                layoutResID, null, false);
        super.setContentView(mBinding.getRoot());
    }

    @Override
    public void initView() {
        setContentView(getLayoutId());
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
    }

    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

}
