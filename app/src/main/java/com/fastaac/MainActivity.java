package com.fastaac;

import android.os.Bundle;

import com.fastaac.base.base.AbsMvvmActivity;
import com.fastaac.databinding.ActivityMainBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;


public class MainActivity extends AbsMvvmActivity<MainVm, ActivityMainBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.btnNet.setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                super.run();
                mViewModel.requestNet();
            }
        }.start());
        mBinding.btnNoNet.setOnClickListener(v -> mViewModel.clickNoNet());

        mBinding.btnEmpty.setOnClickListener(v -> mViewModel.clickNoData());

        mViewModel.getResultLiveData().observe(this, data ->
                mBinding.tvContent.setText(data.getData().get(0).toString()));

        LiveEventBus.get().with(MainVm.EVENT_DATA, TestBean.class).observe(this, testBean ->
                mBinding.tvContent2.setText(testBean.toString()));

    }


    @Override
    protected void retryClick() {
        super.retryClick();
        new Thread() {
            @Override
            public void run() {
                super.run();
                mViewModel.requestNet();
            }
        }.start();
    }
}
