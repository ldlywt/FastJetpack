package com.fastaac.base.util

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