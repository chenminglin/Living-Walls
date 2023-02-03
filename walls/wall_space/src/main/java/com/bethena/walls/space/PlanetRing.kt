package com.bethena.walls.space

import android.graphics.LinearGradient

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
}
