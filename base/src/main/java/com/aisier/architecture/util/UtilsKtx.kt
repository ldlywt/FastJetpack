package com.aisier.architecture.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import com.aisier.architecture.base.BaseApp
import java.util.*

/**
 * <pre>
 *     @author : wutao
 *     e-mail : wutao@neuron.sg
 *     time   : 2020/10/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

inline fun <reified T : Activity> Activity.go(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun stringOf(@StringRes id: Int, vararg formatArgs: Any): String = getString(id, *formatArgs)

fun stringOf(@StringRes id: Int): String = getString(id)

fun EditText.getNotNullText(): String = text?.toString()?.trim() ?: ""

fun EditText.getNotNullUpperCaseText(): String = text?.toString()?.toUpperCase(Locale.ENGLISH)?.trim()
        ?: ""

fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
    return BaseApp.baseApp.resources.getString(id, *formatArgs)
}