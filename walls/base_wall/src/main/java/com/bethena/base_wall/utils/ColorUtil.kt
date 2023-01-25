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
}