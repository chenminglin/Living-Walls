package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import android.view.animation.DecelerateInterpolator
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil

class Flower2 : IFlower {
    var x: Float = 0f
    var y: Float = 0f

    var maxRadius = 0
    var minRadius = 0
    var diffRadius = 0
    var paint = Paint()
    var bitmapPaint = Paint()
    var alpha = 255
    lateinit var shader: RadialGradient
    lateinit var bitmap: Bitmap

    var scaleIncreate = 0.005f

    var allScale = 1f

    lateinit var testBitmap: Bitmap

    override fun provider(context: Context, canvas: Canvas, x: Float, y: Float, ratePer: Float) {
//        x = RandomUtil.nextFloat() * canvas.width
//        y = RandomUtil.nextFloat() * canvas.height / 2
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = ScreenUtil.dp2pxF(context, 3f)


        this.x = x
        this.y = y

        maxRadius = ScreenUtil.dp2px(context, 40f)
        minRadius = ScreenUtil.dp2px(context, 10f)
        diffRadius = ScreenUtil.dp2px(context, 6f)

        bitmap = Bitmap.createBitmap(
            (maxRadius * 2 + paint.strokeWidth).toInt(),
            (maxRadius * 2 + paint.strokeWidth).toInt(), Bitmap.Config.ARGB_8888
        )
        var bitmapCanvas = Canvas(bitmap)
        var bitmapCenterX = bitmap.width / 2f
        var bitmapCenterY = bitmap.height / 2f

        shader = RadialGradient(
            0f,
            0f,
            maxRadius.toFloat(),
            intArrayOf(Color.parseColor("#E45083"), Color.parseColor("#D5ED47")),
            floatArrayOf(0.3f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader

        bitmapCanvas.translate(bitmapCenterX, bitmapCenterY)
        (minRadius..maxRadius step diffRadius).forEach {
            var half = it.toFloat()
            var rect = RectF(-half, -half, +half, +half)
            bitmapCanvas.drawOval(rect, paint)
        }
        scaleIncreate = 0.005f * ratePer
        allScale = randomAllScale()
//        testBitmap = DrawableUtil.getDrawableToBitmap(context,R.drawable.ic_round_stay_current_landscape_24)!!
    }

    val MAX_ALPHA = 255
    val MAX_SCALE = 1.5f
    var scale = 0f
    var realScale = 0f

    override fun draw(canvas: Canvas) {

        canvas.save()
        canvas.translate(x, y)
        canvas.scale(allScale, allScale)
        
        bitmapPaint.alpha = alpha

        var top = -bitmap.height / 2
        var left = -bitmap.width / 2
        canvas.scale(realScale, realScale)
        canvas.drawBitmap(bitmap, top.toFloat(), left.toFloat(), bitmapPaint)

        canvas.restore()
        next(canvas)
    }

    override fun recycler() {
        bitmap.recycle()
    }


    var interpolator = DecelerateInterpolator()

    override fun next(canvas: Canvas) {
        alpha = 255
        if (scale < MAX_SCALE) {
            scale += 0.005f
        } else {
            scale = 0f
        }
        var perScale = scale / MAX_SCALE
        realScale = interpolator.getInterpolation(perScale) * MAX_SCALE
        alpha = (255 * (1 - perScale)).toInt()
        if (alpha < 0) {
            alpha = 0
        }
        LogUtil.d("Flower2", "alpha = $alpha")

//        if (isAnimFinish()){
//            resetAnim(canvas)
//        }
    }

    override fun isAnimFinish(): Boolean {
        return alpha <= 0
    }

    override fun resetAnim(canvas: Canvas, x: Float, y: Float) {
        this.x = x
        this.y = y
        alpha = 255
        realScale = 0f
        scale = 0f
        allScale = randomAllScale()
    }
}