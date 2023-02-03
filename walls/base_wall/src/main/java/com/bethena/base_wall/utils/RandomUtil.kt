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

        fun nextInt(from: Int, until: Int): Int {
            return random.nextInt(until - from) + from
        }

        fun nextFloat(): Float {
            return random.nextFloat()
        }
    }
}