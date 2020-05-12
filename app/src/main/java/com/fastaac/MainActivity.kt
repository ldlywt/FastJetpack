package com.fastaac

import androidx.lifecycle.Observer
import com.fastaac.base.base.BaseVMActivity
import com.fastaac.base.base.BaseResult
import com.fastaac.databinding.ActivityMainBinding
import com.jeremyliao.liveeventbus.LiveEventBus

class MainActivity : BaseVMActivity<MainVm, ActivityMainBinding>() {

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun viewModelClass(): Class<MainVm> {
        return MainVm::class.java
    }

    private fun initData() {
        mBinding.btnNet.setOnClickListener {
            Thread { mViewModel.requestNet() }.start()
        }
        mBinding.btnNoNet.setOnClickListener { mViewModel.clickNoNet() }
        mBinding.btnEmpty.setOnClickListener { mViewModel.clickNoData() }
        mViewModel.resultLiveData.observe(this, Observer {
            mBinding.tvContent.text = it.data?.toString()
        })
        supportFragmentManager.beginTransaction().add(R.id.fl_contain, TestFragment()).commit()
    }

    override fun retryClick() {
        super.retryClick()
        object : Thread() {
            override fun run() {
                super.run()
                mViewModel.requestNet()
            }
        }.start()
    }

    override fun init() {
        initData()
    }
}
