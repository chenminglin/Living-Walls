package com.bethena.walls.space

import android.graphics.*
import com.bethena.base_wall.utils.RandomUtil
import java.util.*

data class Meteor(
    var x: Float,
    var y: Float,
    var tailLength: Float
) {
    var initX = 0f
    var initY = 0f

    init {
        initX = x
        initY = y
    }

    var alpha = 125

    fun draw(canvas: Canvas, paint: Paint) {
        var shader = LinearGradient(
            x, y, x + tailLength, y,
            intArrayOf(Color.WHITE, Color.TRANSPARENT),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.alpha = alpha
        canvas.drawLine(x, y, x + tailLength, y, paint)
        next(canvas)
    }

    var dx = 10
    fun next(canvas: Canvas) {
        x -= dx
//        alpha -= 1
//        var dy = (dx / tan(Math.toRadians(30.0))).toInt()
//        y += dy
        if (alpha <= 0) {
            alpha = 0
        }
        if (x < -canvas.width * 2) {
//            alpha = 255
            restore(canvas)
        }
    }

    fun restore(canvas: Canvas) {
        x = (canvas.width + RandomUtil.nextInt(canvas.width * 2)).toFloat()
        y = RandomUtil.nextInt(canvas.height).toFloat()
    }
}
