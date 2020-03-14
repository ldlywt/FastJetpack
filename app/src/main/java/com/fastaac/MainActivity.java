package com.fastaac;

import android.os.Bundle;

import com.fastaac.base.base.BaseMvvMActivity;
import com.fastaac.databinding.ActivityMainBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

import androidx.annotation.Nullable;


public class MainActivity extends BaseMvvMActivity<MainVm, ActivityMainBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public ActivityMainBinding initBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    public void initData() {
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
        getSupportFragmentManager().beginTransaction().add(R.id.fl_contain, new TestFragment()).commit();
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
