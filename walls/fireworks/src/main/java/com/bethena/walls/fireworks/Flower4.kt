package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import com.bethena.base_wall.utils.ScreenUtil

class Flower4 : IFlower {
    var x = 0f
    var y = 0f
    var paint = Paint()
    var paint2 = Paint()
    var paintBitmap = Paint()

    var camera = Camera()
    var matrix = Matrix()

    var radius1 = 0f
    var radius2 = 0f
    var radius3 = 0f
    var startRadius1 = 0f
    var startRadius2 = 0f
    var startRadius3 = 0f

    var animDegree = 0f

    var alpha = 0

    lateinit var bitmap1: Bitmap
    lateinit var bitmap2: Bitmap
    lateinit var bitmap3: Bitmap

    override fun provider(context: Context, canvas: Canvas, x: Float, y: Float) {
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint2.color = Color.WHITE
        paint2.style = Paint.Style.STROKE

        this.x = x
        this.y = y

        startRadius1 = ScreenUtil.dp2pxF(context, 13f)
        startRadius2 = ScreenUtil.dp2pxF(context, 16f)
        startRadius3 = ScreenUtil.dp2pxF(context, 10f)
        radius1 = ScreenUtil.dp2pxF(context, 70f)
        radius2 = ScreenUtil.dp2pxF(context, 60f)
        radius3 = ScreenUtil.dp2pxF(context, 80f)
        paint.color = Color.parseColor("#C9526D")
        bitmap1 = providerBitmap(radius1, startRadius1, 0)
        paint.color = Color.parseColor("#F7CF46")
        bitmap2 = providerBitmap(radius2, startRadius2, 12)
        paint.color = Color.parseColor("#A453ED")
        bitmap3 = providerBitmap(radius3, startRadius3, 24)
    }

    private fun providerBitmap(radius: Float, startRadius: Float, startDegree: Int): Bitmap {
        var halfHeight = radius / 40f
        var bitmapWidth = (radius + startRadius).toInt() * 2
        var bitmap = Bitmap.createBitmap(bitmapWidth, bitmapWidth, Bitmap.Config.ARGB_8888)
        var bitmapCanvas = Canvas(bitmap)
        bitmapCanvas.translate(bitmapWidth / 2f, bitmapWidth / 2f)
        (startDegree..360 + startDegree step 36).forEach {
            bitmapCanvas.save()
            camera.save()
            camera.rotateZ(it.toFloat())
//            camera.rotateY(88f)
            camera.getMatrix(matrix)
            bitmapCanvas.concat(matrix)
            bitmapCanvas.drawOval(startRadius, -halfHeight, radius, halfHeight, paint)
            camera.restore()
            bitmapCanvas.restore()
        }
        return bitmap
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate(x, y)

        if (animStatus == 3) {
            paintBitmap.alpha = alpha
        }

        drawAnimBitmap(canvas, bitmap1, 0, 1)
        drawAnimBitmap(canvas, bitmap2, 1, 2)
        drawAnimBitmap(canvas, bitmap3, 2, 3)

        canvas.restore()
        next(canvas)
    }

    private fun drawAnimBitmap(
        canvas: Canvas,
        bitmap: Bitmap,
        targetAnimStatus: Int,
        fullAnimStatus: Int
    ) {
        if (animStatus == targetAnimStatus) {
//            canvas.save()
//            var halfHeight = bitmap.height / 2f
//            var baseRectf = RectF(-1f, -1f, 1f, 1f)
//            //设置一个目标正方形框。这个边长是内弧的直径。中心点即为圆心
//            var path = Path()
//            var dDegree = -6f
//            //画一个弧。注意，角度的设置。这样设置，正方型的边长就是直径。方便计算。
//            path.addArc(baseRectf, dDegree, animDegree + dDegree)
//            //向外扩张这个正方形。此时正方形的边长是外弧的直径。
//            baseRectf.inset(-halfHeight, -halfHeight)
//            path.arcTo(baseRectf, dDegree, animDegree + dDegree) //注意角度的设置要反转一下。
//            path.close()
//
//            clipPath(canvas, path)
            paintBitmap.alpha = alpha
            drawBitmap(canvas, bitmap)
//            canvas.restore()
//            canvas.drawPath(path, paint2)
        } else if (animStatus >= 3) {
            drawBitmap(canvas, bitmap)
        } else if (animStatus >= fullAnimStatus) {
            paintBitmap.alpha = 255
            drawBitmap(canvas, bitmap)
        }


    }

    private fun drawBitmap(canvas: Canvas, bitmap: Bitmap) {
        canvas.save()
        canvas.drawBitmap(bitmap, -bitmap.width / 2f, -bitmap.height / 2f, paintBitmap)
        canvas.restore()
    }

    private var animStatus = 0
    override fun next(canvas: Canvas) {
        if (animStatus < 3) {
//            animDegree += 10
            alpha += 10
//            if (animDegree >= 360) {
//                animDegree = 0f
//                animStatus++
//            }
            if (alpha >= 255) {
                animStatus++
                if (animStatus == 3) {
                    alpha = 255
                } else {
                    alpha = 0
                }

            }
        }
        if (animStatus == 3) {
            alpha -= 2
            if (alpha < 0) {
                alpha = 0
            }
        }
//        if (isAnimFinish()){
//            resetAnim(canvas)
//        }
    }

    override fun recycler() {
        bitmap1.recycle()
        bitmap2.recycle()
        bitmap3.recycle()
    }

    override fun isAnimFinish(): Boolean {
        return alpha <= 0 && animStatus == 3
    }

    override fun resetAnim(canvas: Canvas, x: Float, y: Float) {
        this.x = x
        this.y = y
        animDegree = 0f
        animStatus = 0
        alpha = 0
    }
}