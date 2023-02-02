package com.bethena.walls.space

data class Star(
    val x: Float, val y: Float, var radius: Float, var alpha: Int, var color: Int
) {


    var alphaRever: Boolean = false
    fun next() {
        if (!alphaRever) {
            alpha += 3
            if (alpha >= 255) {
                alphaRever = true
                alpha = 255
            }
        } else {
            alpha -= 3
            if (alpha <= 0) {
                alphaRever = false
                alpha = 0
            }
        }


    }
}