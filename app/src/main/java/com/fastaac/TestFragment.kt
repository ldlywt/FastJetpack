package com.fastaac

import android.os.Bundle
import android.view.View
import com.fastaac.base.base.BaseVMFragment
import com.fastaac.databinding.FragmentTestBinding

/**
 * <pre>
 * @author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2020/03/13
 * desc   :
 * version: 1.0
</pre> *
 */
class TestFragment : BaseVMFragment<MainVm, FragmentTestBinding?>(R.layout.fragment_test) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.text.text = "ViewBinding封装"
    }

    override fun initBinding(view: View): FragmentTestBinding? {
       return FragmentTestBinding.bind(view)
    }

    override fun viewModelClass(): Class<MainVm>? {
        return MainVm::class.java
    }


}