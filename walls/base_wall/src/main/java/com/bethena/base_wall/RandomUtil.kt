package com.bethena.base_wall

import java.util.*

object RandomUtil {

    fun randomInt(i:Int): Int{
        return Random().nextInt(i)
    }

    fun between2nums(min: Int, max: Int): Int {
        return min + Random().nextInt(max - min)
    }

    fun between2numsF(min: Float, max: Float): Float {
        return min + Random().nextFloat() * (max - min)
    }
}