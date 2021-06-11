package com.aisier.architecture.util

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * <pre>
 *     @author : wutao
 *     e-mail : wutao@neuron.sg
 *     time   : 2021/5/18
 *     version: 1.1
 * </pre>
 */

inline fun <VM : ViewModel> ComponentActivity.createActivityViewModel(): VM {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
    val viewModel = vbClass[0] as Class<VM>
    return ViewModelProvider(this).get(viewModel)
}

inline fun <VM : ViewModel> Fragment.createFragmentViewModel(): VM {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
    val viewModel = vbClass[0] as Class<VM>
    return ViewModelProvider(this).get(viewModel)
}
