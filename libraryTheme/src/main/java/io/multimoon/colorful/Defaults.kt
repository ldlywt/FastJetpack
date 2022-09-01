package io.multimoon.colorful

import androidx.annotation.StyleRes

/**
 * PrimaryColor：主题颜色。app的主要颜色，即整个屏幕和所有控件的主要颜色，首选颜色。
 * SecondaryColor：提示性颜色。这颜色一般比PrimaryColor亮一些或暗一些，取决于白天模式还是黑暗模式。一般用于提示相关动作或信息，提示性颜色。
 * AccentColor：交互性颜色。这颜色一般用于交互性的控件颜色，比如FloatingButton、TextField、Cursor、ProgressBar、Selection、Links等具体交互性的颜色
 */
data class Defaults(
    val primaryColor: ThemeColorInterface,
    val accentColor: ThemeColorInterface,
    val useDarkTheme: Boolean,
    val translucent: Boolean,
    @StyleRes val customTheme: Int = 0,
)