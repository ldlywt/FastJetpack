package com.fastaac;

import android.os.Bundle;

import com.fastaac.base.base.AbsMvvmActivity;
import com.fastaac.databinding.ActivityMainBinding;


public class MainActivity extends AbsMvvmActivity<MainVm, ActivityMainBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.btn.setOnClickListener(v -> new Thread(){
            @Override
            public void run() {
                super.run();
                mViewModel.requestNet();
            }
        }.start());
    }
}
