package com.aisier.architecture.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.aisier.architecture.anno.FragmentConfiguration
import com.aisier.architecture.util.createActivityViewModel
import com.aisier.architecture.util.createFragmentViewModel

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
 */
abstract class BaseFragment<VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    private var useShareViewModel = false
    private var useEventBus = false

    protected val mViewModel: VM by lazy {
        if (useShareViewModel) requireActivity().createActivityViewModel() else createFragmentViewModel()
    }

    init {
        this.javaClass.getAnnotation(FragmentConfiguration::class.java)?.let {
            useShareViewModel = it.shareViewModel
            useEventBus = it.useEventBus
        }
    }
}