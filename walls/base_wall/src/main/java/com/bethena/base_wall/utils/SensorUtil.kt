package com.bethena.base_wall.utils

import java.util.*

class SensorUtil {

    companion object {
        private var dampingTilt = 8
        fun lowPassAcceleration(input: FloatArray, output: FloatArray) {
            for (i in input.indices) {
                output[i] = output[i] + dampingTilt / 100f * (input[i] - output[i])
            }
        }
    }

    class KalmanFilter {
        private val MEDIAN_BUFFER_LENGTH = 10
        private val medianBuffer = FloatArray(MEDIAN_BUFFER_LENGTH)
        private var medianIndex = 0

        fun smoothValue(newValue: Float): Float {
            medianBuffer[medianIndex] = newValue
            medianIndex = (medianIndex + 1) % MEDIAN_BUFFER_LENGTH
            val sortedArray = medianBuffer.clone()
            Arrays.sort(sortedArray)
            return sortedArray[MEDIAN_BUFFER_LENGTH / 2]
        }

    }

}