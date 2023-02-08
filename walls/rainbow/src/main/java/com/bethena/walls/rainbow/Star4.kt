package com.bethena.walls.rainbow

import android.graphics.Bitmap
import com.bethena.base_wall.utils.RandomUtil

data class Star4(
    val cx: Float, val cy: Float, var bitmap: Bitmap, var alpha: Int
) {

    var degree = RandomUtil.nextInt(180)
    var scale = RandomUtil.nextFloat()
//    var alphaRever: Boolean = false
//    var alphaIncreate = 3f
    private var scaleRever = false
    var scaleIncreate = 0.02f
    fun next() {
        if (!scaleRever) {
            scale += scaleIncreate
            if (scale >= 1.0f) {
                scaleRever = true
                scale = 1.0f
            }
        } else {
            scale -= scaleIncreate
            if (scale <= 0) {
                scaleRever = false
                scale = 0f
            }
        }
//        degree++
//        if (degree > 360) {
//            degree = 0
//        }

    }
}