package com.aisier.architecture.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * <pre>
 *     @author : wutao
 *     e-mail : wutao@neuron.sg
 *     time   : 2020/10/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object GenericUtil {

    fun <T> getGeneric(any: Any, i: Int): T {
        return (any.javaClass
                .genericSuperclass as ParameterizedType)
                .actualTypeArguments[i] as T
    }

}

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