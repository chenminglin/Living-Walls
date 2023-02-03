package com.bethena.walls.space

import android.graphics.*
import com.bethena.base_wall.utils.RandomUtil


data class PlanetBig(
    var x: Float,
    var y: Float,
    var bitmap: Bitmap,
    var rings: ArrayList<PlanetBigRing>,
    var ringDegree: Float,
    var paint: Paint
) {


    /**
     * 重新排序
     * 最外面的星球如果在下面，应该最先画
     */
    fun sortRings(): ArrayList<PlanetBigRing> {
        var newArray = ArrayList<PlanetBigRing>()
        if (rings[2].subDegree > 0 && rings[2].subDegree < 180) {
            newArray.add(rings[0])
            newArray.add(rings[1])
            newArray.add(rings[2])
        } else {
            newArray.add(rings[2])
            newArray.add(rings[1])
            newArray.add(rings[0])
        }
        return newArray
    }


    /**
     * 算出上半部分的球
     */
    var halfPath = Path()
    fun culTopHalfPath() {
        halfPath.reset()
        var top = y - bitmap.height / 2
        var left = x - bitmap.width / 2
        var right = x + bitmap.width / 2
        var bottom = y + bitmap.height / 2

        var cx = x
        var cy = y


        val point1 = PointF(left, top)
        val point2 = PointF(right, top)
        val point3 = PointF(left, bottom)

        val points = arrayOf(point1, point2, point3)
        val radian = Math.toRadians(ringDegree.toDouble())

        for (point in points) {
            val x = point.x
            val y = point.y
            point.x = (((x - cx) * Math.cos(radian) - (y - cy) * Math.sin(radian) + cx).toFloat())
            point.y = (((x - cx) * Math.sin(radian) + (y - cy) * Math.cos(radian) + cy).toFloat())
        }
//        val path = Path()
        halfPath.moveTo(points[0].x, points[0].y)
        halfPath.lineTo(points[1].x, points[1].y)
        halfPath.lineTo(points[2].x, points[2].y)
        halfPath.close()


    }

    data class PlanetBigRing(
        var heightRadius: Float,
        var widthRadius: Float,
        var subPlanet: Planet?
    ) {


        var subDegree = RandomUtil.nextInt(360).toDouble()
        var subDegreeIncreate = 1f

        fun draw(canvas: Canvas, x: Float, y: Float, paint: Paint, subPaint: Paint) {
            var rect = RectF()
            rect.top = y - heightRadius
            rect.left = x - widthRadius
            rect.right = x + widthRadius
            rect.bottom = y + heightRadius

            canvas.drawOval(rect, paint)
            subPlanet?.let {
                var subX = x + widthRadius * Math.cos(Math.toRadians(subDegree))
                var subY = y + heightRadius * Math.sin(Math.toRadians(subDegree))
                subPaint.shader = it.shader
                canvas.drawCircle(subX.toFloat(), subY.toFloat(), it.radius, subPaint)
//                subPaint.color = Color.WHITE
//                subPaint.shader = null
//                subPaint.textSize = 50f
//                canvas.drawText("$subDegree", subX.toFloat(), subY.toFloat(),subPaint)
            }

//            LogUtil.d("subPlanet degree = $subDegree")
            next()
        }

        private fun next() {
            subDegree += subDegreeIncreate
            if (subDegree > 360) {
                subDegree = 0.0
            }
        }
    }

}

