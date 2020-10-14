package com.fastaac

import android.util.Log
import androidx.lifecycle.observe
import com.fastaac.base.base.BaseActivity
import com.fastaac.databinding.ActivityMainBinding
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import kotlin.concurrent.thread

class MainActivity : BaseActivity<MainVm, ActivityMainBinding>() {

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun viewModelClass(): Class<MainVm> = MainVm::class.java

    override fun init() {
        initData()
    }

    private fun initData() {
        mBinding.btnNet.setOnClickListener {
            Thread { mViewModel.requestNet() }.start()
        }
        mBinding.btnNoNet.setOnClickListener { mViewModel.clickNoNet() }
        mBinding.btnEmpty.setOnClickListener { mViewModel.clickNoData() }
        mViewModel.resultLiveData.observe(this) {
            mBinding.tvContent.text = it.data?.toString()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, TestFragment()).commit()
    }

    override fun retryClick() {
        super.retryClick()
        thread {
            mViewModel.requestNet()
        }.start()
    }
}
