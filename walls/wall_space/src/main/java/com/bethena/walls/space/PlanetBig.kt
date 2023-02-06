package com.bethena.walls.space

import android.graphics.*
import com.bethena.base_wall.utils.RandomUtil


data class PlanetBig(
    var x: Float,
    var y: Float,
    var radius: Float,
    var gradientColors: IntArray,
    var pitColor: Int,
    var rings: ArrayList<PlanetBigRing>,
    var ringDegree: Float,
    var ringPaint: Paint
) {
    var bitmap: Bitmap =
        Bitmap.createBitmap((radius * 2).toInt(), (radius * 2).toInt(), Bitmap.Config.ARGB_8888)

    init {
        var canvas = Canvas(bitmap)
        var paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.shader = LinearGradient(
            0f,
            0f,
            radius * 2,
            radius * 2,
            gradientColors,
            floatArrayOf(0.2f, 0.8f),
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(
            (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), radius, paint
        )
        drawPit(canvas, paint, radius - radius / 5, radius / 3, radius / 5)
        drawPit(canvas, paint, radius / 3, radius + radius / 4, radius / 6)
        drawPit(canvas, paint, radius + radius / 2, radius - radius / 9, radius / 9)
    }

    private fun drawPit(canvas: Canvas, paint: Paint, cx: Float, cy: Float, cradius: Float) {
        var path11 = Path()
        var path12 = Path()
        var path1 = Path()
        path1.addCircle(cx, cy, cradius, Path.Direction.CW)
        var path2 = Path()
        path2.addCircle(
            cx, cy + radius / 20, cradius, Path.Direction.CW
        )
        path11.op(path1, path2, Path.Op.INTERSECT)
        path12.op(path1, path2, Path.Op.DIFFERENCE)
        paint.shader = null
        paint.color = pitColor
        paint.alpha = 80
        canvas.drawPath(path11, paint)
        paint.alpha = 128
        canvas.drawPath(path12, paint)
    }


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
        var heightRadius: Float, var widthRadius: Float, var subPlanet: Planet?
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
                it.x = subX.toFloat()
                it.y = subY.toFloat()
                subPaint.shader = it.newShader()
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

