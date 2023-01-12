package com.bethena.walls_test

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import com.bethena.base_wall.utils.LogUtil


class MainTestActivity : AppCompatActivity() {
//    var wallEngineHandler: BaseEngineHandler? = null

    lateinit var toolbar: Toolbar
    var choreographer = Choreographer.getInstance()
    var frameCallback = FrameCallback()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = " "
        toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                return true
            }
        })
        setSupportActionBar(toolbar)

//        setSupportActionBar()

        var surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                TestApp.wallEngineHandler?.create(holder)
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

    }

    override fun onResume() {
        super.onResume()
        window.decorView.post {
            TestApp.wallEngineHandler?.onVisibilityChanged(true)
        }
        choreographer.postFrameCallback(frameCallback)
    }

    override fun onPause() {
        super.onPause()
        TestApp.wallEngineHandler?.onVisibilityChanged(false)
        choreographer.removeFrameCallback(frameCallback)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        return super.onMenuOpened(featureId, menu)
    }

    //
    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        return super.onCreatePanelMenu(featureId, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_pause -> {
                TestApp.wallEngineHandler?.pause()
            }
            R.id.menu_config -> {
                var intent = Intent(this@MainTestActivity, ConfigTestActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class FrameCallback : Choreographer.FrameCallback {
        private var mLastFrameTime: Long = 0
        private var mFrameCount: Int = 0
        override fun doFrame(frameTimeNanos: Long) {
            if (mLastFrameTime == 0L) {
                mLastFrameTime = frameTimeNanos
            }
            val diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f //得到毫秒，正常是 16.66 ms
            if (diff > 500) {
                val fps = (mFrameCount * 1000L).toDouble() / diff
                mFrameCount = 0
                mLastFrameTime = 0
                toolbar.title = "帧率：${Math.round(fps).toInt()}fps"
                LogUtil.d("doFrame: $fps")
            } else {
                ++mFrameCount
            }
            if (this@MainTestActivity.isFinishing) {
                return
            }
            if (this@MainTestActivity.isDestroyed) {
                return
            }

            choreographer.postFrameCallback(frameCallback)
        }
    }
}