package com.ldlywt.colorful

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.WindowManager
import androidx.annotation.StyleRes

class ColorfulDelegate(private val config: ColorThemeConfig) {
    
    fun applyTheme(activity: Activity, @StyleRes theme: Int = 0) {
        if (theme != 0) {
            activity.setTheme(theme)
        } else {
            activity.setTheme(config.customTheme)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ColorTheme().getTranslucent()) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }
    
    fun getDarkTheme(): Boolean = config.useDarkTheme
    
    fun getTranslucent(): Boolean = config.translucent
    
    fun getCustomTheme(): Int = config.customTheme
    
    fun edit(): ThemeEditor {
        return ThemeEditor(config)
    }
    
    fun clear(context: Context) {
        edit().resetPrefs(context)
    }
}