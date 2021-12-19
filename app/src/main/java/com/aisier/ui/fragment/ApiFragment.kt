package com.aisier.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseFragment
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.FragmentApiBinding
import com.aisier.network.observer.observeState
import com.aisier.vm.ApiViewModel

class ApiFragment : BaseFragment(R.layout.fragment_api) {

    private val mBinding by viewBinding(FragmentApiBinding::bind)
    private val mViewModel by activityViewModels<ApiViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        mViewModel.wxArticleLiveData.observeState(this) {
            onSuccess = { result: List<WxArticleBean>? ->
                result?.let {
                    val wxArticleBean: WxArticleBean = result[0]
                    mBinding.text.text =
                        wxArticleBean.name + "   是否展示： " + wxArticleBean.visible + "\n" + result[1].name + "   是否展示： " + result[1].visible
                }
            }
        }
    }

}