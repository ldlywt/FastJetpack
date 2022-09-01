package io.multimoon.colorful

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.WindowManager
import androidx.annotation.StyleRes

class ColorfulDelegate(
    private var primaryColor: ThemeColorInterface,
    private var accentColor: ThemeColorInterface,
    private var darkTheme: Boolean,
    private var translucent: Boolean,
    @StyleRes private val customTheme: Int = 0,
) {
    
    fun apply(activity: Activity, override: Boolean = true, baseTheme: BaseTheme = BaseTheme.THEME_APPCOMPAT) {
        if (override) {
            when (baseTheme) {
                BaseTheme.THEME_MATERIAL -> activity.setTheme(if (darkTheme) R.style.Colorful_Dark else R.style.Colorful_Light)
                BaseTheme.THEME_APPCOMPAT -> activity.setTheme(if (darkTheme) R.style.Colorful_AppCompat_Dark else R.style.Colorful_AppCompat_Light)
            }
        }
        
        activity.theme.applyStyle(primaryColor.primaryStyle(), true)
        activity.theme.applyStyle(accentColor.accentStyle(), true)
        if (customTheme != 0) {
            activity.theme.applyStyle(customTheme, true)
            Log.d("COLORFUL", "Overriding native colorful theme with user provided theme at StyleRes $customTheme")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Colorful().getTranslucent()) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            activity.setTaskDescription(ActivityManager.TaskDescription(null, null, Colorful().getPrimaryColor().getColorPack().dark().asInt()))
        }
    }
    
    fun getPrimaryColor(): ThemeColorInterface = primaryColor
    
    fun getAccentColor(): ThemeColorInterface = accentColor
    
    fun getDarkTheme(): Boolean = darkTheme
    
    fun getTranslucent(): Boolean = translucent
    
    fun getCustomTheme(): Int = customTheme
    
    fun edit(): ThemeEditor {
        return ThemeEditor(primaryColor, accentColor, darkTheme, translucent, customTheme)
    }
    
    fun clear(context: Context) {
        edit().resetPrefs(context)
    }
    
    internal fun getThemeString(): String {
        return primaryColor.themeName + ":" + accentColor.themeName + ":" + darkTheme + ":" + customTheme + ":" + translucent
    }
}