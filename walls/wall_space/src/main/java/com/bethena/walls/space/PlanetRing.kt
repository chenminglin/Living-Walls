package com.bethena.walls.space

import android.graphics.LinearGradient

data class PlanetRing(
    val x: Float,
    val y: Float,
    val radius: Float,
    var shader: LinearGradient,
    val radiusRingX: Float,
    val radiusRingY: Float,
    var colorRing: Int
)
