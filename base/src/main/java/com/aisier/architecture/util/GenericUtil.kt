package com.aisier.architecture.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * <pre>
 *     @author : wutao
 *     e-mail : wutao@neuron.sg
 *     time   : 2021/5/18
 *     version: 1.1
 * </pre>
 */

inline fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater): VB {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[1].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}

inline fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[1].getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    return inflate.invoke(null, inflater, container, false) as VB
}

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
