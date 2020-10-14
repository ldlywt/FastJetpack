package com.fastaac.base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.fastaac.base.anno.FragmentConfiguration

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

    protected val mViewModel: VM by lazy {
        if (shareViewModel) getActivityViewModel(viewModelClass()) else getFragmentViewModel(viewModelClass())
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

    protected abstract fun viewModelClass(): Class<VM>

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    protected open fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T =
            mFragmentProvider.get(modelClass)

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T =
            mActivityProvider.get(modelClass)

    init {
        this.javaClass.getAnnotation(FragmentConfiguration::class.java)?.let {
            shareViewModel = it.shareViewModel
            useEventBus = it.useEventBus
        }
    }
}