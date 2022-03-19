package com.aisier.architecture.ktx

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.Locale

fun EditText.getNotNullText(): String = text?.toString()?.trim() ?: ""

fun EditText.getNotNullUpperCaseText(): String = getNotNullText().toUpperCase(Locale.ENGLISH)

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