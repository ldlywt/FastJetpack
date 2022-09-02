package com.aisier

import com.aisier.architecture.base.BaseApp
import com.ldlywt.colorful.ColorThemeConfig
import com.ldlywt.colorful.initColorful

class App : BaseApp() {
    
    override fun onCreate() {
        super.onCreate()
        val colorThemeConfig = ColorThemeConfig(
            useDarkTheme = true,
            translucent = false,
            customTheme = R.style.Theme_Red
        )
        initColorful(this, colorThemeConfig)
    }
}