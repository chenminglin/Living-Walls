package com.bethena.base_wall.utils

import android.graphics.Color
import androidx.annotation.ColorInt


object ColorUtil {

    @ColorInt
    fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    fun isDarkColor(@ColorInt color: Int): Boolean {
        val isDark = Color.red(color) < 128 && Color.green(color) < 128 && Color.blue(color) < 128
        return isDark
    }

    fun randomColor(): Int {
        val hsv = FloatArray(3)
        hsv[0] = Math.random().toFloat() * 360 // 色相范围为 0 ~ 360
        hsv[1] = 0.5f + Math.random().toFloat() * 0.5f // 饱和度范围为 0.5 ~ 1.0
        hsv[2] = 0.5f + Math.random().toFloat() * 0.5f // 值范围为 0.5 ~ 1.0
        return Color.HSVToColor(hsv)
    }
}