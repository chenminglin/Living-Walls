package com.bethena.walls.space

import android.graphics.LinearGradient
import android.graphics.Shader

data class Planet(
    var x: Float,
    var y: Float,
    val radius: Float,
    var colors: IntArray,
) {

    val initY = y
    val initX = x
    var afterSensorX = initX
    var afterSensorY = initY

    var shader: LinearGradient = newShader()

    private fun newShader(): LinearGradient {
        return LinearGradient(
            x - radius,
            y - radius,
            x + radius,
            y + radius,
            colors,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private var isRever = false
    fun next() {
        if (afterSensorX > x) {
            x++
        } else if (afterSensorX < x) {
            x--
        }
//        if (!isRever) {
//            y++
//            if (y > initY + 50) {
//                isRever = true
//            }
//        } else {
//            y--
//            if (y < initY - 50) {
//                isRever = false
//            }
//        }
//        newShader()
    }
}
