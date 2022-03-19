package com.aisier.architecture.ktx

import androidx.annotation.StringRes
import com.aisier.architecture.base.BaseApp

fun stringOf(@StringRes id: Int, vararg formatArgs: Any): String = getString(id, *formatArgs)

fun stringOf(@StringRes id: Int): String = getString(id)

fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
    return BaseApp.instance.resources.getString(id, *formatArgs)
}