package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import com.bethena.base_wall.utils.RandomUtil

interface IFlower {
    fun provider(context: Context, canvas: Canvas, x: Float, y: Float)
    fun draw(canvas: Canvas)
    fun recycler()
    fun next(canvas: Canvas)
    fun isAnimFinish(): Boolean
    fun resetAnim(canvas: Canvas, x: Float, y: Float)

    fun clipPath(canvas: Canvas, path: Path) {
        canvas.clipPath(path)
    }

    fun clipOutPath(canvas: Canvas, path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path)
        } else {
            canvas.clipPath(path, Region.Op.DIFFERENCE)
        }
    }

    fun randomX(canvas: Canvas): Float {
        return RandomUtil.nextInt(canvas.width).toFloat()
    }

    fun randomY(canvas: Canvas): Float {
        return canvas.height / 2 * RandomUtil.nextFloat()
    }
}