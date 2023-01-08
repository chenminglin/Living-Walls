package com.bethena.walls.circle

import android.animation.ValueAnimator
import android.view.animation.*
import com.bethena.base_wall.LogUtils

data class Circle(
    val centerX: Float, val centerY: Float,
    var radius: Float,
    val minRadius: Float, val maxRadius: Float,
    val stepDiff: Float,
    val paintWidth: Float
) {


    private var isAnimRever: Boolean = false

    //    var interpolator: Interpolator = CycleInterpolator(2f)
//    var interpolator: Interpolator = BounceInterpolator()
//    var interpolator: Interpolator = OvershootInterpolator()
    var interpolator: Interpolator = LinearInterpolator()
    private var realRadius = radius
    private var repeatCount = 0
    fun nextStep() {
        if (!isAnimRever) {
            realRadius += stepDiff
            if (realRadius > maxRadius) {
                isAnimRever = true
            }
        } else {
            realRadius -= stepDiff
            if (realRadius <= minRadius) {
                repeatCount++
                isAnimRever = false
            }
        }

//        if (repeatCount >= 1) {
//            return
//        }

        var input = realRadius / (maxRadius - minRadius)
        radius = interpolator.getInterpolation(input) * realRadius

        if (radius < 0.005) {
            radius = 0f
        }
//        LogUtils.d("Circle nextStep  minRadius = $minRadius maxRadius = $maxRadius realRadius = $realRadius radius = $radius repeatCount = $repeatCount")
    }


    override fun toString(): String {
        return super.toString()
    }
}