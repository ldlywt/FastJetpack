package com.aisier.architecture.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.aisier.architecture.anno.FragmentConfiguration

/**
 * author : wutao
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var useEventBus = false

    init {
        this.javaClass.getAnnotation(FragmentConfiguration::class.java)?.let {
            useEventBus = it.useEventBus
        }
    }
}