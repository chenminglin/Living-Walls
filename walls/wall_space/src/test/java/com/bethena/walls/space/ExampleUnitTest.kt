package com.bethena.walls.space

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.sin

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var length = 100
        var x = Math.sin(Math.toRadians(30.0)) * length
        println("x = "+ x)
        println("y = "+ Math.cos(Math.toRadians(30.0)) * length)

       var y2 = x / Math.tan(Math.toRadians(30.0))

        println("y2 = $y2")
    }
}