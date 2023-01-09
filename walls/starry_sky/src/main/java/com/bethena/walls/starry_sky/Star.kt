package com.bethena.walls.starry_sky

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.bethena.base_wall.LogUtils

data class Star(
    val starBitmap: Bitmap,
    val initX: Float,
    val initY: Float,
    val initScale: Float,
    val initAlpha: Int,
    val initDegree: Int
) {

    var x = 0f
    var y = 0f
    var increateY = 10
    var increateDegree = 1
    var scale = 1f
    var alpha = 0
    var degree = 0
    var bitmapSize = 0
    var maxY = 0
    var middleX = intArrayOf()
    var interpolatorY = OvershootInterpolator()
    var interpolatorX = DecelerateInterpolator()
    var isBeat = false
    var increateScale = 0f

    //控制闪烁分几段
    var partOfMaxYtoFlash = 5
//    var partOfMaxYtoScale = 12
    var realX = 0f
//    var interpolatorX = PathInterpolator()

    init {
        x = initX
        realX = x
        y = initY
        scale = initScale
        alpha = initAlpha
        degree = initDegree
    }


    fun reset() {
        x = initX
        realX = x
        y = -bitmapSize.toFloat()
        scale = initScale
        alpha = initAlpha
        degree = initDegree
        isBeatRever = false
    }


    fun draw(canvas: Canvas, paint: Paint) {
        paint.alpha = culAlpha()
        var positionMatrix = Matrix()
//        var realY = interpolator.getInterpolation(y / maxY) * y
        var realY = y
        culX()
        positionMatrix.setTranslate(realX, realY)
        var scaleMatrix = Matrix()
        scaleMatrix.setScale(scale, scale, 0.5f, 0.5f)
        var rotateMatrix = Matrix()
        rotateMatrix.setRotate(degree.toFloat())
        positionMatrix.preConcat(scaleMatrix)
        positionMatrix.preConcat(rotateMatrix)
        canvas.drawBitmap(starBitmap!!, positionMatrix, paint)

        next(canvas)
    }


    //计算当前透明度，在当前高度
    var isAlphaAnimRever = false
    fun culAlpha(): Int {
        if (partOfMaxYtoFlash == 0) {
            return 255
        }
        if (y < 0) {//一开始是0
            return 0
        }
        var alpha = 255
        var dHeight = (maxY / partOfMaxYtoFlash)//每段闪烁的高度
        var per = (y % dHeight) / dHeight//当前y在每段的百分比
//        var halfPer = (y % (dHeight / 2)) / (dHeight / 2)
        var halfPer = if (per < 0.5f) {
            per * dHeight / (dHeight / 2)
        } else {
            (per - 0.5f) * dHeight / (dHeight / 2)
        }

        if (per == 0.5f) {
            alpha = 255
        } else if (per == 0f) {
            alpha = 0
        } else if (per < 0.5) {
            alpha = (255 * halfPer).toInt()
        } else {
            alpha = (255 * (1 - halfPer)).toInt()
        }

        LogUtils.d("alpha = $alpha  per = $per  halfPer = $halfPer  y = $y   y % dHeight = ${y % dHeight}")
        return alpha
    }

    var currentSection = -1
    fun culX() {
        var dHeight = (maxY / middleX.size)
        var n = (y / dHeight).toInt()//当前处于哪一段
        if (currentSection != n) {
            currentSection = n
            x = realX
        }
        var per = y % dHeight / dHeight//当前段落百分比
//        var dx = Math.abs(x - middleX[n]) * interpolatorX.getInterpolation(per)
        var dx = Math.abs(x - middleX[n]) * per
        if (x >= middleX[n].toFloat()) {
            realX = x - dx
        } else {
            realX = x + dx
        }
        LogUtils.d("x = $x realX = $realX currentSection = $currentSection middleX[0] = ${middleX[0]} middleX[1] = ${middleX[1]}")
    }

    var isEnd = false

    var isBeatRever = false

    fun next(canvas: Canvas) {
//        if (!isEnd) {
        y += increateY
        degree += increateDegree
        if (isBeat) {
            if (!isBeatRever) {
                scale += increateScale
                if (scale > 1.5f) {
                    isBeatRever = true
                }
            } else {
                scale -= increateScale
                if (scale < 0.5f) {
                    isBeatRever = false
                }
            }


        }

//        }
        if (y >= canvas.height) {
            reset()
//            isEnd = true
        }
    }

}
