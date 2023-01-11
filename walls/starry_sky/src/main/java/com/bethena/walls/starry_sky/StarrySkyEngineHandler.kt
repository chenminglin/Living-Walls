package com.bethena.walls.starry_sky

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.DrawableUtil
import com.bethena.base_wall.LogUtils
import com.bethena.base_wall.ScreenUtil
import kotlin.random.Random

class StarrySkyEngineHandler(context: Context?) : BaseEngineHandler(context) {
    private var mPaint: Paint = Paint()
    private var starBitmaps = arrayListOf<Bitmap>()
    private var backgroundColor: Int? = null
    private var starCount = 30
    private var stars = arrayListOf<Star>()
    private var bitmapSize = 0

    private var meshColor = 0

    override fun onCreate(surfaceHolder: SurfaceHolder?) {
        super.onCreate(surfaceHolder)
        mPaint.isAntiAlias = true
        mContext?.let {
            var starColor = ContextCompat.getColor(it, R.color.starry_sky_star)
            var starBitmap1 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 0.5f)
            var starBitmap2 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 1f)
            var starBitmap3 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 1.5f)
            starBitmaps.add(starBitmap1!!)
            starBitmaps.add(starBitmap2!!)
            starBitmaps.add(starBitmap3!!)

            backgroundColor = ContextCompat.getColor(it, R.color.starry_sky_background)
            bitmapSize = it.resources.getDimensionPixelSize(R.dimen.starry_sky_star_default_size)
        }

        meshColor = Color.parseColor("#54000000")

    }

    override fun onVisibilityChanged(visible: Boolean) {
        if (mContext == null) {
            return
        }

        if (visible) {
            initStars()
//            testDraw()
            doDraw()
        } else {
            mainHandler.removeCallbacksAndMessages(null)
        }
    }

    override fun pause() {
        mainHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    //测试用方法
    private fun testDraw() {
        var canvas = lockCanvas()
        var matrix1 = Matrix()
        matrix1.setTranslate(100f, 100f)
        var matrix2 = Matrix()
        matrix2.setScale(2f, 2f)
        matrix1.preConcat(matrix2)
        canvas?.drawBitmap(starBitmaps[0], matrix1, mPaint)
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 1f)
        canvas?.drawRect(100f, 100f, 100f + bitmapSize, 100f + bitmapSize, mPaint)
        mSurfaceHolder?.unlockCanvasAndPost(canvas)
    }

    private fun doDraw() {
        mainHandler.postDelayed({
            if (stars.size == 0) {
                return@postDelayed
            }
            var canvas = lockCanvas() ?: return@postDelayed

            canvas.save()
            if (backgroundColor != null) {
                canvas.drawColor(backgroundColor!!)
            }

            stars.forEach {
                it.draw(canvas, mPaint)
            }

//            canvas.drawColor(meshColor)

            canvas.restore()
            mSurfaceHolder?.unlockCanvasAndPost(canvas)
            doDraw()
        }, 1000 / refreshRate)
    }

    private fun initStars() {
        stars.clear()
        var canvas: Canvas? = lockCanvas() ?: return
        var canvasWidth = canvas!!.width - bitmapSize
        var canvasHeight = canvas.height - bitmapSize

        var ratePer = refreshRate / ScreenUtil.MAX_RATE.toFloat()

        for (i in 0 until starCount) {
            var x = Random.nextInt(canvasWidth)
            var y = Random.nextInt(canvasHeight)
//            var scale = RandomUtil.between2numsF(0.5f, 3f)
            var indexBitmap = Random.nextInt(starBitmaps.size)
            var starBitmap = starBitmaps[indexBitmap]
            var star = Star(starBitmap, x.toFloat(), y.toFloat(), 1f, 255, 0f)
//            star.reset()

            star.bitmapSize = starBitmap.height
            star.maxY = bitmapSize + canvas.height + bitmapSize
//            star.partOfMaxYtoFlash = Random.nextInt(4)
            star.middleX =
                intArrayOf(Random.nextInt(canvasWidth), Random.nextInt(canvasWidth))



            star.increateY = 10 * (1 - ratePer)

            star.increateDegree = (Random.nextDouble(-4.0, 5.0) * (1 - ratePer)).toFloat()
//            LogUtils.d("Random.nextInt(3) = ${Random.nextInt(3)}")
            star.isBeat = (Random.nextInt(3) == 1)
            star.increateScale = Random.nextDouble(0.01, 0.04).toFloat() * (1 - ratePer)
            star.partOfMaxYtoFlash = Random.nextInt(3, 6)
//            star.isBeat = true
            stars.add(star)
            LogUtils.d(star.toString())
        }
        mSurfaceHolder?.unlockCanvasAndPost(canvas)
    }

    override fun newConfigFragment(): Fragment {
        return StarrySkySettingFragment()
    }
}