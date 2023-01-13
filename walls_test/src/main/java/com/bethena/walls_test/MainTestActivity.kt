package com.bethena.walls_test

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.*
import android.view.*
import android.view.PixelCopy.OnPixelCopyFinishedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.utils.LogUtil
import com.permissionx.guolindev.PermissionX
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainTestActivity : AppCompatActivity() {
//    var wallEngineHandler: BaseEngineHandler? = null

    lateinit var toolbar: Toolbar
    var choreographer = Choreographer.getInstance()
    var frameCallback = FrameCallback()
    lateinit var surfaceView: SurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = " "

        setSupportActionBar(toolbar)

//        setSupportActionBar()

        surfaceView = findViewById(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                TestApp.wallEngineHandler?.create(holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
            R.id.menu_cut -> {

                PermissionX.init(this).permissions(
                    arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ).request { allGranted, grantedList, deniedList ->
                    if (allGranted) {

//                        TestApp.wallEngineHandler?.isCutPicture = true

                        var bitmap = Bitmap.createBitmap(
                            surfaceView.width/2, surfaceView.height/2, Bitmap.Config.RGB_565
                        )

                        var canvas = Canvas(bitmap)
                        surfaceView.draw(canvas)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            PixelCopy.request(surfaceView,bitmap,object :OnPixelCopyFinishedListener{
                                override fun onPixelCopyFinished(p0: Int) {
                                    // 将位图压缩为 PNG 格式
                                    val byteArrayOutputStream = ByteArrayOutputStream()
                                    bitmap.let {
                                        it.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream)
                                        // 保存图像到文件
                                        val PIC_SAVE_PATH_DIR by lazy {
                                            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath}${File.separator}Camera${File.separator}"
                                        }
                                        var dir = PIC_SAVE_PATH_DIR
                                        var dirFile = File(dir)
                                        if (!dirFile.exists()) {
                                            dirFile.mkdirs()
                                        }

                                        var path = dir + "cut_${System.currentTimeMillis()}.png"

                                        val fileOutputStream = FileOutputStream(path)
                                        fileOutputStream.write(byteArrayOutputStream.toByteArray())
                                        fileOutputStream.close()
                                        byteArrayOutputStream.close()
//                            it.recycle()
                                    }
                                }
                            }, Handler(Looper.getMainLooper()))
                        }


                    }
                }


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