package com.aisier.architecture.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.aisier.architecture.anno.FragmentConfiguration
import com.aisier.architecture.util.GenericUtil

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var shareViewModel = false
    private var useEventBus = false
    protected var mBinding: VB? = null
        private set

    protected val mViewModel: VM by lazy {
        if (shareViewModel) getActivityViewModel<VM>(GenericUtil.getGeneric(this, 0))
        else getFragmentViewModel(GenericUtil.getGeneric(this, 0))
    }

    private val mFragmentProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }
    private val mActivityProvider: ViewModelProvider by lazy {
        ViewModelProvider(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = initBinding(view)
    }

    protected abstract fun initBinding(view: View): VB

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    protected open fun <VM : ViewModel> getFragmentViewModel(modelClass: Class<VM>): VM =
            mFragmentProvider.get(modelClass)

    protected open fun <VM : ViewModel> getActivityViewModel(modelClass: Class<VM>): VM =
            mActivityProvider.get(modelClass)

    init {
        this.javaClass.getAnnotation(FragmentConfiguration::class.java)?.let {
            shareViewModel = it.shareViewModel
            useEventBus = it.useEventBus
        }
    }
}