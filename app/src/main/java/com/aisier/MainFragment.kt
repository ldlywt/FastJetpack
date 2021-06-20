package com.aisier

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
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
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val mBinding by viewBinding(FragmentMainBinding::bind)
    private val mViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.resultUiLiveData.observe(viewLifecycleOwner) { uiState ->
            uiState.successData?.let {
                mBinding.text.text =
                    it[0].showName + "   是否展示： " + it[0].isShow + "\n" + it[1].showName + "   是否展示： " + it[1].isShow
            }
        }
    }

}