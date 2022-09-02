package com.ldlywt.colorful

import android.app.Application
import android.content.Context
import android.util.Log

var mInstance: ColorfulDelegate? = null
const val darkThemeKey: String = "dark_theme"
const val customThemeKey: String = "custom_theme"
const val translucentKey: String = "translucent"

fun ColorTheme(): ColorfulDelegate {
    mInstance?.let { return it }
    throw Exception("Colorful has not been initialized! Please call initColorful(app:Application) in your application class before working with Colorful!")
}

fun initColorful(app: Application, colorThemeConfig: ColorThemeConfig) {
    val prefs = app.getSharedPreferences(ThemeEditor.PREF_NAME, Context.MODE_PRIVATE)
    
    mInstance = ColorfulDelegate(
        ColorThemeConfig(prefs.getBoolean(darkThemeKey, colorThemeConfig.useDarkTheme),
            prefs.getBoolean(translucentKey, colorThemeConfig.translucent),
            prefs.getInt(customThemeKey, colorThemeConfig.customTheme)))
    
    Log.d("COLORFUL", "defaults: ${colorThemeConfig.customTheme} ---- save: ${prefs.getInt(customThemeKey, colorThemeConfig.customTheme)}")
}