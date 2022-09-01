package com.aisier

import com.aisier.architecture.base.BaseApp
import io.multimoon.colorful.Defaults
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.initColorful

class App : BaseApp() {
    
    override fun onCreate() {
        super.onCreate()
        val defaults = Defaults(
            primaryColor = ThemeColor.GREEN,
            accentColor = ThemeColor.BLUE,
            useDarkTheme = true,
            translucent = false)
        initColorful(this, defaults)
    }
}