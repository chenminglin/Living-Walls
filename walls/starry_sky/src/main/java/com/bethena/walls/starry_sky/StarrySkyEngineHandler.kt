package com.bethena.walls.starry_sky

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.DrawableUtil
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil
import kotlin.random.Random

class StarrySkyEngineHandler(context: Context?) : BaseEngineHandler(context) {
    private var mPaint: Paint = Paint()
    private var starBitmaps = arrayListOf<Bitmap>()

    var starCount = StarrySkyConst.KEY_INIT_STARS_COUNT
    var speed = StarrySkyConst.KEY_INIT_STARS_SPEED

    private var stars = arrayListOf<Star>()
    private var bitmapSize = 0
    var backgroundColor = 0
    var starColor = 0
    var mashColor = StarrySkyConst.KEY_INIT_MASH_COLOR

    var backgroundColors = arrayListOf<Int>()
    var starColors = arrayListOf<Int>()

    init {
        mPaint.isAntiAlias = true
        mContext?.let {
            bitmapSize = it.resources.getDimensionPixelSize(R.dimen.starry_sky_star_default_size)
        }
    }

    override fun create() {
        mContext?.let { it ->
            starColors.clear()
            backgroundColors.clear()
            var color1 = ContextCompat.getColor(it, R.color.starry_sky_star)
            starColors.add(color1)
            starColors.add(ContextCompat.getColor(it, R.color.starry_sky_star2))
            starColors.add(ContextCompat.getColor(it, R.color.starry_sky_star3))

            backgroundColors.add(ContextCompat.getColor(it, R.color.starry_sky_background))
            backgroundColors.add(ContextCompat.getColor(it, R.color.starry_sky_background2))

            spUtils?.let {
                StarrySkyConst.let { const ->
                    starCount = it.getInt(
                        const.KEY_STARS_COUNT, const.KEY_INIT_STARS_COUNT
                    )
                    speed = it.getFloat(
                        const.KEY_STARS_SPEED, const.KEY_INIT_STARS_SPEED
                    )
                    starColor = it.getInt(const.KEY_STAR_COLOR, starColors[0])
                    backgroundColor = it.getInt(const.KEY_BACKGROUND_COLOR, backgroundColors[0])
                    mashColor = ColorUtil.adjustAlpha(
                        Color.BLACK,
                        it.getInt(const.KEY_MASH_PERCENT, 0) / 100f
                    )
                }

            }
            if (starBitmaps.size > 0) {
                starBitmaps.forEach { bitmap ->
                    bitmap.recycle()
                }
                starBitmaps.clear()
            }

            var starBitmap1 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 0.5f)
            var starBitmap2 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 1f)
            var starBitmap3 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star2, starColor, 1.5f)
            starBitmaps.add(starBitmap1!!)
            starBitmaps.add(starBitmap2!!)
            starBitmaps.add(starBitmap3!!)
        }
        initStars()
    }

    override fun create(surfaceHolder: SurfaceHolder?) {
        super.create(surfaceHolder)


    }


    override fun onVisibilityChanged(visible: Boolean) {
        super.onVisibilityChanged(visible)

    }

    override fun pause() {
        mainHandler.removeCallbacksAndMessages(null)
    }


    override fun onDestroy() {
        super.onDestroy()
        starBitmaps.forEach {
            it.recycle()
        }
        starBitmaps.clear()
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

     override fun doDraw() {
        mainHandler.postDelayed({
            if (stars.size == 0) {
                return@postDelayed
            }
            var canvas = lockCanvas() ?: return@postDelayed

            canvas.save()
            canvas.drawColor(backgroundColor)

            stars.forEach {
                it.draw(canvas, mPaint)
            }

            canvas.drawColor(mashColor)
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
        LogUtil.d("initStars canvasHeight = $canvasHeight")

        var ratePer = refreshRate / ScreenUtil.MAX_RATE.toFloat()

        for (i in 0 until starCount) {
            var x = Random.nextInt(canvasWidth)
            var y = Random.nextInt(canvasHeight)
            LogUtil.d("initStars y = $y")
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



            star.increateY = (10 * (1 - ratePer)) * speed

            star.increateDegree = (Random.nextDouble(-4.0, 5.0) * (1 - ratePer)).toFloat()
//            LogUtils.d("Random.nextInt(3) = ${Random.nextInt(3)}")
            star.isBeat = (Random.nextInt(3) == 1)
            star.increateScale = Random.nextDouble(0.01, 0.04).toFloat() * (1 - ratePer)
            star.partOfMaxYtoFlash = Random.nextInt(3, 6)
//            star.isBeat = true
            stars.add(star)
//            LogUtils.d(star.toString())
        }
        mSurfaceHolder?.unlockCanvasAndPost(canvas)
    }

    override fun newConfigFragment(): Fragment {
        return StarrySkySettingFragment()
    }


}