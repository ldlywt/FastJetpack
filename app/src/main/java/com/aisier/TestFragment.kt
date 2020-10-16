package com.aisier

import android.os.Bundle
import android.view.View
import com.aisier.architecture.anno.FragmentConfiguration
import com.aisier.architecture.base.BaseFragment
import com.aisier.databinding.FragmentTestBinding

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/03/13
 * desc   :
 * version: 1.0
</pre> *
 */
@FragmentConfiguration(shareViewModel = false)
class TestFragment : BaseFragment<MainViewModel, FragmentTestBinding>(R.layout.fragment_test) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding?.text?.text = "ViewBinding封装"
    }

    override fun initBinding(view: View): FragmentTestBinding = FragmentTestBinding.bind(view)

}