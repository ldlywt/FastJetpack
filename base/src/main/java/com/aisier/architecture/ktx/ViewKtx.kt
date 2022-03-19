package com.aisier.architecture.ktx

import android.graphics.drawable.GradientDrawable
import android.view.View

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

/**
 * 自定义圆角矩形
 */
fun View.setRoundRectBg(color: Int, cornerRadius: Int) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius.toFloat())
    }
}