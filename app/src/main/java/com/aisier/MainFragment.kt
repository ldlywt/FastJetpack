package com.aisier

import android.os.Bundle
import androidx.lifecycle.observe
import com.aisier.architecture.anno.FragmentConfiguration
import com.aisier.architecture.base.BaseFragment
import com.aisier.databinding.FragmentMainBinding

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
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.resultLiveData.observe(viewLifecycleOwner) {
            mBinding?.text?.text = it.data?.get(0).toString()
        }
    }

}