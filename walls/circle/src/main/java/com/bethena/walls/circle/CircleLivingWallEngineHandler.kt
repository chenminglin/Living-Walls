package com.bethena.walls.circle

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.bethena.base_wall.LogUtils
import com.bethena.base_wall.RandomUtil
import com.bethena.base_wall.ScreenUtil

class CircleLivingWallEngineHandler {
    private var mContext: Context? = null
    private var mHolder: SurfaceHolder? = null
    private var mMainHandler: Handler? = null
    private var paint = Paint()
    private var isVisible = false;
    private var mSensorManager: SensorManager? = null
    private var mSensorGravity: Sensor? = null
    private var circlese = arrayListOf<Circle>()
    private var interpolators = InterpolatorUtil.getInterpolators()
    private var mSensorGravityEventListener: SensorEventListener =
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val xAcceleration = event.values[0]
                    val yAcceleration = event.values[1]
                    val zAcceleration = event.values[2]

                    LogUtils.d("xAcceleration = $xAcceleration yAcceleration = $yAcceleration zAcceleration = $zAcceleration")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

        }

    constructor(context: Context?) {
        mContext = context
    }

    fun onCreate(surfaceHolder: SurfaceHolder?) {
        mHolder = surfaceHolder
        mHolder?.setFormat(PixelFormat.RGBA_8888)
        paint.isAntiAlias = true
        paint.color = Color.parseColor("#88ffffff")
        paint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 3f)
        paint.style = Paint.Style.STROKE
        LogUtils.d("LivingWallsEngine onCreate-----")
        mSensorManager =
            mContext?.getSystemService(WallpaperService.SENSOR_SERVICE) as SensorManager
        mSensorGravity = mSensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    fun onVisibilityChanged(visible: Boolean) {
        isVisible = visible
        mMainHandler = Handler(Looper.getMainLooper())

        if (visible) {
            mSensorManager?.registerListener(
                mSensorGravityEventListener,
                mSensorGravity,
                SensorManager.SENSOR_DELAY_NORMAL
            )
            initCirclese()
            doDraw()
        } else {
            mMainHandler?.removeCallbacksAndMessages(null)
            mSensorManager?.unregisterListener(mSensorGravityEventListener)
        }
    }



    private fun doDraw() {
        if (!isVisible) {
            LogUtils.d("doDraw return..")
            return
        }

        if (circlese.size == 0) {
            return
        }
        var canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                mHolder?.lockHardwareCanvas()
            } catch (e: Exception) {
                mHolder?.lockCanvas()
            }
//                        mHolder?.lockCanvas()
        } else {
            mHolder?.lockCanvas()
        }
        mMainHandler?.postDelayed(Runnable {
            mHolder?.let { it ->
//                LogUtils.d("doDrawing..")

                if (canvas == null) {
                    return@let
                }
                var width = canvas.width
                var height = canvas.height
//                    canvas.drawARGB(0, 0, 0, 0)
                canvas.save()
//                    canvas.
                canvas.drawColor(Color.BLACK)
//                    canvas.drawARGB(0, 0, 0, 0)

//                canvas.rotate(degrees, width / 2f, height / 2f)

//                canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)

                circlese.forEach { c ->
                    paint.strokeWidth = c.paintWidth
//                    if (c.radius < 0) {
//                        return@forEach
//                    }
                    canvas.drawCircle(c.centerX, c.centerY, c.radius, paint)
//                    LogUtils.d(c.toString())
                    c.nextStep()
                }
                canvas.restore()
                it.unlockCanvasAndPost(canvas)
                doDraw()
            }
        }, 20)
//        canvas?.drawColor(Color.BLACK)
//        circlese.forEach {
//            var anim = ValueAnimator.ofFloat(it.minRadius, it.maxRadius)
//            anim.addUpdateListener { a ->
//                LogUtils.d("anim..")
//                canvas?.save()
//                var raduis = a.getAnimatedValue() as Float
//                it.radius = raduis
//                paint.strokeWidth = it.paintWidth
//                canvas?.drawCircle(it.centerX, it.centerY, it.radius, paint)
//                canvas?.restore()
//                if (canvas!=null){
//                    mHolder?.unlockCanvasAndPost(canvas)
//                }
////                it.nextStep()
//            }
//            it.anim = anim
//            anim.duration = 1000
//            anim.repeatCount = ValueAnimator.INFINITE
//            anim.repeatMode = ValueAnimator.REVERSE
//            anim.start()
//        }
    }


    fun initCirclese() {
        mContext?.apply {
            circlese.clear()
            var startX = 0
            var startY = 0
            var windowWidth = ScreenUtil.getScreenWidth(this)
            var windowHeight = ScreenUtil.getScreenHeight(this)
            var minRadius = ScreenUtil.dp2pxF(this, 0f)
            var maxRadius = ScreenUtil.dp2pxF(this, 25f)
            var margin = ScreenUtil.dp2px(this, 20f)
            var incre_dp_start = ScreenUtil.dp2pxF(this, 0.1f)
            var incre_dp_end = ScreenUtil.dp2pxF(this, 0.3f)
            var incre_dp = RandomUtil.between2numsF(incre_dp_start, incre_dp_end)

            for (x in startX..windowWidth step (margin + maxRadius * 2).toInt()) {
                for (y in startY..windowHeight step (margin + maxRadius * 2).toInt()) {
                    var radius = RandomUtil.between2numsF(minRadius, maxRadius)
                    var paintWidth =
                        RandomUtil.between2numsF(
                            ScreenUtil.dp2pxF(this, 0.5f),
                            ScreenUtil.dp2pxF(this, 3f)
                        )
                    var circle = Circle(
                        x.toFloat(),
                        y.toFloat(),
                        radius,
                        minRadius,
                        maxRadius,
                        incre_dp,
                        paintWidth
                    )
                    circle.interpolator = interpolators[RandomUtil.randomInt(interpolators.size)]
                    circlese.add(circle)
                }
            }
//            var radius = RandomUtil.between2numsF(minRadius, maxRadius)
//            var paintWidth =
//                RandomUtil.between2numsF(
//                    ScreenUtil.dp2pxF(this, 0.5f),
//                    ScreenUtil.dp2pxF(this, 3f)
//                )
//            var circle = Circle(
//                windowWidth.toFloat() / 2,
//                windowHeight.toFloat() / 2,
//                radius,
//                minRadius,
//                maxRadius,
//                incre_dp,
//                paintWidth
//            )
//            circle.interpolator = interpolators[RandomUtil.randomInt(interpolators.size)]
//            circlese.add(circle)
        }

        LogUtils.d("init finish")

    }

}