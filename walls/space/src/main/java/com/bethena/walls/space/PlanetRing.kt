package com.bethena.walls.space

import android.graphics.LinearGradient
import android.graphics.RectF

data class PlanetRing(
    var x: Float,
    var y: Float,
    val radius: Float,
    var shader: LinearGradient,
    val radiusRingX: Float,
    val radiusRingY: Float,
    var colorRing: Int
){
    val initX = x
    val initY = y

    var ringRectF = RectF(
        x - radiusRingX,
        y - radiusRingY,
        x + radiusRingX,
        y + radiusRingY
    )

    var circleRectf =
        RectF(x - radius, y - radius, x + radius, y + radius)
}
