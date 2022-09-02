package com.ldlywt.colorful

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.StyleRes
import androidx.core.app.ActivityCompat

class ThemeEditor(private val config: ColorThemeConfig) {
    
    companion object {
        val PREF_NAME: String = "io.multimoon.colorful.colorvals"
    }
    
    fun setDarkTheme(darkTheme: Boolean): ThemeEditor {
        this.config.useDarkTheme = darkTheme
        return this
    }
    
    fun setCustomThemeOverride(@StyleRes customTheme: Int): ThemeEditor {
        this.config.customTheme = customTheme
        return this
    }
    
    fun setTranslucent(translucent: Boolean): ThemeEditor {
        this.config.translucent = translucent
        return this
    }
    
    fun apply(context: Context, callback: () -> Unit = { Log.d("Colorful", "Callback omitted") }) {
        applyEdits(context, config)
        callback()
    }
    
    fun applyAndRestart(activity: Activity) {
        applyEdits(activity.applicationContext, config)
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            // 有的手机会闪屏
            ActivityCompat.recreate(activity)
        } else {
            // 不能在栈底Activity中使用
            activity.startActivity(activity.intent)
            activity.finish()
            activity.overridePendingTransition(0, 0)
        }
    }
    
    private fun applyEdits(context: Context, config: ColorThemeConfig) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(darkThemeKey, config.useDarkTheme)
            .putBoolean(translucentKey, config.translucent)
            .putInt(customThemeKey, config.customTheme)
            .apply()
        mInstance = ColorfulDelegate(config)
    }
    
    fun resetPrefs(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}