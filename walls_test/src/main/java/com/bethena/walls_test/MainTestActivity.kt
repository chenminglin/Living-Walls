package com.bethena.walls_test

import android.os.Bundle
import android.view.Choreographer
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.LogUtils
import com.bethena.walls.starry_sky.StarrySkyLivingWallEngineHandler


class MainTestActivity : AppCompatActivity() {
    var wallEngineHandler: BaseEngineHandler? = null

    lateinit var tv_fps:TextView

    var choreographer = Choreographer.getInstance()
    var frameCallback = FrameCallback()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_fps = findViewById(R.id.tv_fps)

        var surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)
        wallEngineHandler = StarrySkyLivingWallEngineHandler(this)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                wallEngineHandler?.onCreate(holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })

        findViewById<Button>(R.id.btn_pause).setOnClickListener {
            wallEngineHandler?.pause()
        }


    }

    override fun onResume() {
        super.onResume()
        window.decorView.post {
            wallEngineHandler?.onVisibilityChanged(true)
        }
        choreographer.postFrameCallback(frameCallback)
    }

    override fun onPause() {
        super.onPause()
        wallEngineHandler?.onVisibilityChanged(false)
        choreographer.removeFrameCallback(frameCallback)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    inner class FrameCallback : Choreographer.FrameCallback {
        private var mLastFrameTime: Long = 0
        private var mFrameCount:Int = 0
        override fun doFrame(frameTimeNanos: Long) {
            if (mLastFrameTime == 0L) {
                mLastFrameTime = frameTimeNanos
            }
            val diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f //得到毫秒，正常是 16.66 ms
            if (diff > 500) {
                val fps = (mFrameCount * 1000L).toDouble()/ diff
                mFrameCount = 0
                mLastFrameTime = 0
                tv_fps.setText("帧率：${Math.round(fps).toInt()}fps")
                LogUtils.d( "doFrame: $fps")
            } else {
                ++mFrameCount
            }
            if (this@MainTestActivity.isFinishing){
                return
            }
            if (this@MainTestActivity.isDestroyed){
                return
            }

            choreographer.postFrameCallback(frameCallback)
        }
    }
}