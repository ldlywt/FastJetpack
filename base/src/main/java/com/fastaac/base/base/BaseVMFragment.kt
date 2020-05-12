package com.fastaac.base.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseVMFragment<VM : AbsViewModel?, VB : ViewBinding?>(contentLayoutId: Int) : BaseFragment(contentLayoutId) {

    protected var mViewModel: VM? = null
    protected var mBinding: VB? = null
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = initBinding(view)
        mViewModel = getFragmentViewModel(viewModelClass())

    }


    protected abstract fun initBinding(view: View): VB

    protected abstract fun viewModelClass(): Class<VM>?

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }


    protected open fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>?): T? {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        modelClass?.let {
            return mFragmentProvider?.get(modelClass)
        }
        return null
    }

    protected open fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T? {
        if (mActivityProvider == null) {
            mActivity?.let {
                mActivityProvider = ViewModelProvider(it)
            }
        }
        return mActivityProvider?.get(modelClass)
    }
}