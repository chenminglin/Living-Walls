package com.bethena.walls.starry_sky

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.*

class StarrySkyEngineHandler(context: Context?) : BaseEngineHandler(context) {
    private var mPaint: Paint = Paint()
    private var starBitmaps = arrayListOf<Bitmap>()

    var starCount = StarrySkyConst.KEY_INIT_STARS_COUNT
    var speed = StarrySkyConst.KEY_INIT_STARS_SPEED

    var baseIncreateY = 0f


    private var stars = arrayListOf<Star>()
    private var bitmapSize = 0
    var backgroundColor = 0
    var starColor = 0

    var backgroundColors = arrayListOf<Int>()
    var starColors = arrayListOf<Int>()

    init {
        LogUtil.d("StarrySkyEngineHandler init mContext = $mContext")
        mPaint.isAntiAlias = true
        mContext?.let {
            bitmapSize = it.resources.getDimensionPixelSize(R.dimen.starry_sky_star_default_size)
            starColors.clear()
            backgroundColors.clear()
            var color1 = ContextCompat.getColor(it, R.color.starry_sky_star)
            starColors.add(color1)
            starColors.add(ContextCompat.getColor(it, R.color.starry_sky_star2))
            starColors.add(ContextCompat.getColor(it, R.color.starry_sky_star3))

            backgroundColors.add(ContextCompat.getColor(it, R.color.starry_sky_background))
            backgroundColors.add(ContextCompat.getColor(it, R.color.starry_sky_background2))
            backgroundColors.add(ContextCompat.getColor(it, R.color.starry_sky_background3))
            baseIncreateY = ScreenUtil.dp2pxF(it, 0.05f);

        }
        spUtils.let {
            StarrySkyConst.let { const ->
                starCount = it.getInt(
                    const.KEY_STARS_COUNT, const.KEY_INIT_STARS_COUNT
                )
                speed = it.getFloat(
                    const.KEY_STARS_SPEED, const.KEY_INIT_STARS_SPEED
                )
                starColor = it.getInt(const.KEY_STAR_COLOR, starColors[0])
                backgroundColor = it.getInt(const.KEY_BACKGROUND_COLOR, backgroundColors[0])
            }
        }

    }

    override fun initVariableMaterial() {
        mContext?.let { it ->
            spUtils.let {
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
                        Color.BLACK, it.getInt(const.KEY_MASH_PERCENT, 0) / 100f
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
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star, starColor, 0.5f)
            var starBitmap2 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star, starColor, 1f)
            var starBitmap3 =
                DrawableUtil.getDrawableToBitmap(it, R.drawable.ic_baseline_star, starColor, 1.3f)
            starBitmaps.add(starBitmap1!!)
            starBitmaps.add(starBitmap2!!)
            starBitmaps.add(starBitmap3!!)
        }
        initStars()
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        super.surfaceCreated(surfaceHolder)

    }


    override fun onVisibilityChanged(visible: Boolean) {
        super.onVisibilityChanged(visible)
        LogUtil.d("StarrySkyEngineHandler onVisibilityChanged")

    }

    override fun pause() {
        super.pause()
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
        mPaint.textSize = ScreenUtil.dp2pxF(mContext!!, 50f)
        canvas?.drawRect(100f, 100f, 100f + bitmapSize, 100f + bitmapSize, mPaint)
        mSurfaceHolder?.unlockCanvasAndPost(canvas)
    }

    override fun doDraw() {
//        LogUtil.d("doDraw")
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
        }, refreshTime)
    }

    override fun getMashValue(): Int {
        return spUtils.getInt(StarrySkyConst.KEY_MASH_PERCENT, 0)
    }


    private fun initStars() {
        stars.clear()
        var canvas: Canvas? = lockCanvas() ?: return
        var canvasWidth = canvas!!.width - bitmapSize
        var canvasHeight = canvas.height - bitmapSize
        //是否横屏
        var isLangscape = canvasWidth >= canvasHeight
        LogUtil.d("initStars canvasWidth = $canvasWidth")
        LogUtil.d("initStars canvasHeight = $canvasHeight")
        LogUtil.d("initStars starCount = $starCount")
        var horizontalCount = 5
        var verticalCount = starCount / horizontalCount
        //高度分成几段，星星的数量必须是5的倍数
        var eachWidth = canvasWidth / horizontalCount
        var eachHeight = canvasHeight / verticalCount

        var ratePer = refreshTime / baseRefreshTime.toFloat()
        LogUtil.d("initStars ratePer = $ratePer")
        var increateY = baseRefreshTime * ratePer * speed * baseIncreateY
        LogUtil.d("initStars increateY = $increateY")

        for (pieceX in 0 until horizontalCount) {
            for (pieceY in 0 until verticalCount) {
                LogUtil.d("initStars pieceX = $pieceX  pieceY = $pieceY eachWidth = $eachWidth eachHeight = $eachHeight")
                var x = RandomUtil.nextInt(eachWidth) + pieceX * eachWidth
                var y = RandomUtil.nextInt(eachHeight) + pieceY * eachHeight
                LogUtil.d("initStars x = $x  y = $y")
//            var scale = RandomUtil.between2numsF(0.5f, 3f)
                var indexBitmap = RandomUtil.nextInt(starBitmaps.size)
                var starBitmap = starBitmaps[indexBitmap]
                var star = Star(starBitmap, x.toFloat(), y.toFloat(), 1f, 255, 0f)
//            star.reset()

                star.bitmapSize = starBitmap.height
                star.maxY = bitmapSize + canvas.height + bitmapSize
//            star.partOfMaxYtoFlash = Random.nextInt(4)
                var randomXSection = RandomUtil.nextInt(2)
                if (isLangscape) {
                    if (star.initX >= 0 && star.initX <= canvasWidth / 2) {
                        if (randomXSection == 0) {
                            star.middleX = intArrayOf(RandomUtil.nextInt(canvasWidth / 2))
                        } else {
                            star.middleX = intArrayOf(
                                RandomUtil.nextInt(canvasWidth / 2),
                                RandomUtil.nextInt(canvasWidth / 2)
                            )
                        }
                    } else {
                        if (randomXSection == 0) {
                            star.middleX = intArrayOf(
                                RandomUtil.nextInt(canvasWidth / 2, canvasWidth)
                            )
                        } else {
                            star.middleX = intArrayOf(
                                RandomUtil.nextInt(canvasWidth / 2, canvasWidth),
                                RandomUtil.nextInt(canvasWidth / 2, canvasWidth)
                            )
                        }

                    }
                } else {
                    if (randomXSection == 0) {
                        star.middleX = intArrayOf(RandomUtil.nextInt(canvasWidth))
                    } else {
                        star.middleX =
                            intArrayOf(
                                RandomUtil.nextInt(canvasWidth),
                                RandomUtil.nextInt(canvasWidth)
                            )
                    }

                }


                star.increateY = increateY

                star.increateDegree = (RandomUtil.nextInt(-3, 3) * ratePer).toFloat()
//            LogUtils.d("Random.nextInt(3) = ${Random.nextInt(3)}")
                star.isBeat = (RandomUtil.nextInt(3) == 1)
                star.increateScale = RandomUtil.nextInt(1, 3).toFloat() * ratePer * 0.01f
                star.partOfMaxYtoFlash = RandomUtil.nextInt(3, 6)
//            star.isBeat = true
                stars.add(star)
//            LogUtils.d(star.toString())
            }
        }



        mSurfaceHolder?.unlockCanvasAndPost(canvas)
    }

    override fun newConfigFragment(): Fragment {
        return StarrySkyConfigFragment()
    }


}