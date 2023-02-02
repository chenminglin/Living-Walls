package com.bethena.walls_test

import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.VectorDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bethena.base_wall.utils.DrawableUtil


class CanvasTestActivity : AppCompatActivity() {
    //    var wallEngineHandler: BaseEngineHandler? = null
    var planet: VectorDrawableCompat? = null
    lateinit var toolbar: Toolbar
    lateinit var surfaceView: SurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = " "

        setSupportActionBar(toolbar)
//        setSupportActionBar()

        var cloud = ContextCompat.getDrawable(this, R.drawable.cloud)
        if (cloud is VectorDrawable) {
        }






        surfaceView = findViewById(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {


                doDraw(holder)

            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })
    }

    var paint = Paint()

    var degree = 45

    fun doDraw(holder: SurfaceHolder) {
        var canvas = holder.lockHardwareCanvas()
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawColor(Color.BLACK)
        var path = Path()
//        path.lineTo(400f, 400f)
        path.moveTo(100f, 100f)
        path.rQuadTo(700f, 100f, 600f, 600f)
        canvas.drawPath(path, paint)


        var bitmapCloud = DrawableUtil.getDrawableToBitmap(this, R.drawable.cloud)
        var bitmapPlanet = DrawableUtil.getDrawableToBitmap(this, R.drawable.planet)


//        canvas.drawBitmap(bitmapCloud!!, 0f, 0f, paint)
////        canvas.drawBitmap(bitmapPlanet!!, 400f, 800f, paint)
//        var planet = VectorMasterDrawable(this, R.drawable.planet)
//        var topPath = planet?.getPathModelByName("top")
//        topPath.fillColor = Color.WHITE
//        planet?.draw(canvas)
        val cx = (canvas.width / 2).toFloat()
        val cy = (canvas.height / 2).toFloat()
        val rectWidth = 100f
        val rectHeight = 200f
        val left = cx - rectWidth / 2
        val top = cy - rectHeight / 2
        val right = cx + rectWidth / 2
        val bottom = cy + rectHeight / 2
        var degree = 45f
        val radian = degree * Math.PI / 180
        var path1 = Path()
        path1.addRect(left, top, right, bottom, Path.Direction.CCW)
        val matrix = Matrix()
        matrix.postRotate(radian.toFloat(), cx, cy)
        path1.transform(matrix)
        canvas.drawPath(path1, paint)
        Log.d("aaa","degree = $degree")

        degree++
        if (degree >= 360) {
            degree = 0f
        }
        holder.unlockCanvasAndPost(canvas)
        Handler(Looper.getMainLooper()).postDelayed({
            doDraw(holder)
        },30)
    }


}