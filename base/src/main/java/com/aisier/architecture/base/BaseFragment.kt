package com.aisier.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.aisier.architecture.anno.FragmentConfiguration
import com.aisier.architecture.util.createActivityViewModel
import com.aisier.architecture.util.createFragmentViewModel
import com.aisier.architecture.util.getViewBinding

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var useShareViewModel = false
    private var useEventBus = false
    protected var mBinding: VB? = null
        private set

    protected val mViewModel: VM by lazy {
        if (useShareViewModel) requireActivity().createActivityViewModel() else createFragmentViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = getViewBinding(inflater, container)
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    init {
        this.javaClass.getAnnotation(FragmentConfiguration::class.java)?.let {
            useShareViewModel = it.shareViewModel
            useEventBus = it.useEventBus
        }
    }
}