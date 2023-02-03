package com.bethena.walls.space

data class Star(
    val x: Float, val y: Float, var radius: Float, var alpha: Int, var color: Int
) {

    var alphaRever: Boolean = false
    var alphaIncreate = 3f
    fun next() {
        if (!alphaRever) {
            alpha = (alphaIncreate + alpha).toInt()
            if (alpha >= 255) {
                alphaRever = true
                alpha = 255
            }
        } else {
            alpha = (alpha - alphaIncreate).toInt()
            if (alpha <= 0) {
                alphaRever = false
                alpha = 0
            }
        }
    }
}