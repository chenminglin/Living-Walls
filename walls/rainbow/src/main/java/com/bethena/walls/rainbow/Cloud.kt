package com.bethena.walls.rainbow

import android.graphics.Bitmap
import android.graphics.Canvas
import com.bethena.base_wall.utils.LogUtil

data class Cloud(val bitmap: Bitmap, var left: Float, var top: Float) {

    var leftIncreate = 2f

    fun next(canvas: Canvas) {
        left += leftIncreate
        if (left + bitmap.width > canvas.width + bitmap.width * 2) {
            left = (0 - bitmap.width * 2).toFloat()
        }
        LogUtil.d("left = $left")
        LogUtil.d("bitmap.width = ${bitmap.width * 2}")
        LogUtil.d("canvas.width = ${canvas.width}")
    }
}
