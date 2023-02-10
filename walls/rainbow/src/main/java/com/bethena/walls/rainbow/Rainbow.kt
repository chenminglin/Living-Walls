package com.bethena.walls.rainbow

import android.graphics.*
import com.bethena.base_wall.utils.RandomUtil

data class Rainbow(
    val color: Int,
    val color11: Int,
    val cx: Float,
    val cy: Float,
    val paintWidth:Float,
    val radius: Float,
    val starBitmap: Bitmap
) {

    var paintHalfWidth = 0f

    var arcRect = RectF(
        cx - radius,
        cy - radius,
        cx + radius,
        cy + radius,
    )

    var arcShadowRect = lazy {
        RectF(
            cx - radius - paintHalfWidth,
            cy - radius - paintHalfWidth,
            cx + radius + paintHalfWidth,
            cy + radius + paintHalfWidth,
        )
    }

    var degreeDiff = RandomUtil.nextInt(5)
    var degree = degreeDiff.toFloat()

    var starDegree = 0f

    val STATE_FALL = 0
    val STATE_ALPHA = 1
    val STATE_FINISH = 2
    var state = STATE_FALL
    var alpha = 255f

    private var positionMatrix = Matrix()
    private var rotateMatrix = Matrix()
    var degreeIncreate = 0.2f
    var alphaIncreate = 0.02f
    var starDegreeIncreate = 2f

    var shader = lazy {
//        var startPer =
        RadialGradient(
            cx, cy, radius, intArrayOf(color11, color), floatArrayOf(0.76f, 1f),
            Shader.TileMode.CLAMP
        )
    }


    fun next() {
        if (state == STATE_FALL) {
            if (!isFallFinish()) {
                degree += degreeIncreate
            }
            starDegree += starDegreeIncreate
            if (starDegree >= 360) {
                starDegree = 0f
            }
        } else if (state == STATE_ALPHA) {
            alpha -= alphaIncreate
            if (alpha <= 0) {
                alpha = 0f
                state = STATE_FINISH
            }
        }
    }

    fun getStarMatrix(starX: Float, starY: Float, bitWidth: Float, bitHeight: Float): Matrix {
//        starMatrix.reset()
        positionMatrix.reset()
        rotateMatrix.reset()
        positionMatrix.setTranslate(
            starX,
            starY
        )
        rotateMatrix.setRotate(
            starDegree,
            bitWidth / 2,
            bitHeight / 2
        )
        positionMatrix.preConcat(rotateMatrix)
        return positionMatrix
    }

    fun isFallFinish(): Boolean {
        return degree >= 100
//        return degree >= 360
    }

    fun isFallState(): Boolean {
        return state == STATE_FALL
    }

    fun isAllFinish(): Boolean {
        return state == STATE_FINISH
    }

    fun toAlpha() {
        state = STATE_ALPHA
    }

    fun isAlpha(): Boolean {
        return state == STATE_ALPHA
    }

    fun reset() {
        degree = degreeDiff.toFloat()
        alpha = 255f
        state = STATE_FALL
    }
}
