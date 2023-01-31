package com.bethena.walls.thunder_breath

import android.graphics.Path
import com.bethena.base_wall.utils.LogUtil
import java.util.*


class ThunderPath {
    var path = Path()
    var random = Random()
    val TAG = "ThunderPath"
    var startX = 0f
    var endX = 0f
    fun randomPath(width: Float, height: Float) {
        LogUtil.d(TAG, "width = $width" + " height = $height")
        random.setSeed(System.currentTimeMillis())
        randomStart(width)
        var midPointCount = random.nextInt(8) + 3
        LogUtil.d(TAG, "midPointCount = $midPointCount")
        var eachX = width / midPointCount
        var eachY = height / midPointCount
        (1 .. midPointCount).forEach {
//            var x = random.nextFloat() * width
            var x = random.nextFloat() * eachX + (eachX * it)
            var y = random.nextFloat() * eachY + (eachY * it)
            LogUtil.d(TAG, "x = $x" + " y = $y")
            path.lineTo(x, y)
        }
//        randomEnd(width, height)
    }

    private fun randomStart(width: Float) {
        startX = random.nextFloat() * width
        LogUtil.d(TAG, "startX = $startX")
        path.moveTo(startX, 0f)
    }

    private fun randomEnd(width: Float, height: Float) {
        var halfWidth = width * 0.5f
        if (isStartAtLeft(width)) {//开始在左边，终止在右边
            endX = random.nextFloat() * halfWidth + halfWidth
        } else {
            endX = random.nextFloat() * halfWidth
        }

        LogUtil.d(TAG, "endX = $endX")
        path.lineTo(endX, height)
    }

    private fun isStartAtLeft(width: Float): Boolean {
        return startX in 0f..width
    }
}