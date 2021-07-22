package com.aisier.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseFragment
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.FragmentMainBinding
import com.aisier.util.TimerShareLiveData
import com.aisier.vm.MainViewModel

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
        mViewModel.wxArticleLiveData.observeState(this) {
            onSuccess { result: List<WxArticleBean>? ->
                result?.let {
                    val wxArticleBean: WxArticleBean = result[0]
                    mBinding.text.text =
                            wxArticleBean.name + "   是否展示： " + wxArticleBean.visible + "\n" + result[1].name + "   是否展示： " + result[1].visible
                }
            }
        }

        TimerShareLiveData.get().observe(viewLifecycleOwner) {
            Log.i("wutao--> ", "MainFragment: $it")
        }
    }

}