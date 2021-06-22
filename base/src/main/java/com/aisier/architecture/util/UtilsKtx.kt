package com.aisier.architecture.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import com.aisier.architecture.base.BaseApp
import java.util.*

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */

inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun stringOf(@StringRes id: Int, vararg formatArgs: Any): String = getString(id, *formatArgs)

fun stringOf(@StringRes id: Int): String = getString(id)

fun EditText.getNotNullText(): String = text?.toString()?.trim() ?: ""

fun EditText.getNotNullUpperCaseText(): String = getNotNullText().toUpperCase(Locale.ENGLISH)

fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
    return BaseApp.instance.resources.getString(id, *formatArgs)
}

fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApp.instance, message, duration).show()
}

//may only available on real device
fun EditText.openKeyBoard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

//may only available on real device
fun EditText.hideKeyBoard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.clickWithLimit(intervalMill: Int = 500, block: ((v: View?) -> Unit)) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > intervalMill) {
                block(v)
                last = System.currentTimeMillis()
            }
        }
    })
}