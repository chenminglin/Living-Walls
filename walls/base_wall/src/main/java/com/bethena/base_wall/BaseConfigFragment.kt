package com.bethena.base_wall

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider

abstract class BaseConfigFragment<T : BaseEngineHandler> : BaseFragment() {
    var onWallCheckListener: OnWallCheckListener? = null
    lateinit var surfaceView: SurfaceView

    lateinit var engineHandler: T


    fun checkWall() {
        onWallCheckListener?.onWallCheck()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineHandler = newEngineHandler()
        setHasOptionsMenu(true)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_config
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceView = view.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                onSurfaceCreated(view, holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {
                onSurfaceChanged(holder, format, width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                onSurfaceDestroyed(holder)
            }
        })

        var bar = view.findViewById<BottomAppBar>(R.id.bar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(bar)

        initBtnCheck(view)

        LayoutInflater.from(requireContext()).inflate(inflateId(), view as ViewGroup, true)

    }

    fun initBtnCheck(view: View) {
        var fab_check = view.findViewById<FloatingActionButton>(R.id.fab_check)
        fab_check.setOnClickListener {
            checkWall()
        }
    }

    override fun onResume() {
        super.onResume()
        surfaceView.post {
            engineHandler.onVisibilityChanged(true)
        }
    }

    override fun onPause() {
        super.onPause()
        engineHandler.onVisibilityChanged(false)
    }


    abstract fun menuId(): Int

    abstract fun onSurfaceCreated(view: View, holder: SurfaceHolder)

    abstract fun onSurfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int, height: Int
    )

    abstract fun onSurfaceDestroyed(holder: SurfaceHolder)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuId(), menu)
    }

    abstract fun inflateId(): Int

    abstract fun newEngineHandler(): T

    fun initBaseMashSlider(slider: Slider,slideDefaultValue:Float){
        slider.valueFrom = 0f
        slider.valueTo = 80f
        slider.stepSize = 10f
        slider.value = slideDefaultValue
    }

}