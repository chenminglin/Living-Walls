package com.bethena.base_wall.utils

import java.util.*

class RandomUtil {
    companion object {
        var random = Random(System.nanoTime())

        fun nextInt(): Int {
            return random.nextInt()
        }

        fun nextInt(bound: Int): Int {
            return random.nextInt(bound)
        }

        fun nextFloat(): Float {
            return random.nextFloat()
        }
    }
}