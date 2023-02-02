package com.bethena.walls.space

import android.graphics.LinearGradient

data class Planet(
    val x: Float,
    val y: Float,
    val radius: Float,
    var shader: LinearGradient
)
