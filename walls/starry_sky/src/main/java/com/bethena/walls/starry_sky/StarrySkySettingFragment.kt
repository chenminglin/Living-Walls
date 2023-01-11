package com.bethena.walls.starry_sky

import android.os.Bundle
import android.view.*
import android.view.SurfaceHolder.Callback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.LogUtils
import com.bethena.base_wall.colorpicker.ColorItem
import com.bethena.base_wall.colorpicker.ColorPickerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider


class StarrySkySettingFragment : Fragment(), Slider.OnChangeListener, Slider.OnSliderTouchListener {

    lateinit var engineHandler: StarrySkyEngineHandler
    lateinit var surfaceView: SurfaceView
    var isSliderTouching = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineHandler = StarrySkyEngineHandler(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starry_sky_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<View>()
        surfaceView = view.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                engineHandler.onCreate(holder)
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_config, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config -> {
                var bottomSheetDialog = BottomSheetDialog(requireContext())
                bottomSheetDialog.setContentView(R.layout.fragment_starry_sky_bottom)
                var slider_star_num = bottomSheetDialog.findViewById<Slider>(R.id.slider_star_num)
                var slider_speed = bottomSheetDialog.findViewById<Slider>(R.id.slider_speed)
                slider_star_num?.let {
                    var value = engineHandler.spUtils?.getInt(
                        StarrySkyConst.KEY_STARS_COUNT, StarrySkyConst.KEY_INIT_STARS_COUNT
                    )
                    it.value = value!!.toFloat()
                    it.addOnChangeListener(this@StarrySkySettingFragment)
                    it.addOnSliderTouchListener(this@StarrySkySettingFragment)
                }
                slider_speed?.let {
                    var value = engineHandler.spUtils?.getFloat(
                        StarrySkyConst.KEY_STARS_SPEED, StarrySkyConst.KEY_INIT_STARS_SPEED
                    )
                    it.value = value!!
                    it.addOnChangeListener(this@StarrySkySettingFragment)
                    it.addOnSliderTouchListener(this@StarrySkySettingFragment)
                }

                bottomSheetDialog.setOnDismissListener {
                    slider_star_num?.let {
                        engineHandler.spUtils?.putInt(
                            StarrySkyConst.KEY_STARS_COUNT,
                            it.value.toInt()
                        )
                        it.removeOnChangeListener(this)
                        it.removeOnSliderTouchListener(this)
                    }

                    slider_speed?.let {
                        engineHandler.spUtils?.putFloat(
                            StarrySkyConst.KEY_STARS_SPEED,
                            it.value
                        )
                        it.removeOnChangeListener(this)
                        it.removeOnSliderTouchListener(this)
                    }
                }
                var rv_background_color_picker = bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_background_color_picker)
                var colorItems = arrayListOf<ColorItem>()
                engineHandler.backgroundColors.forEach {
                    colorItems.add(ColorItem(it,it == engineHandler.backgroundColor))
                }
                rv_background_color_picker?.adapter = ColorPickerAdapter(colorItems)
                bottomSheetDialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        view?.postDelayed({
            activity?.let {
                if (!fromUser) {
                    return@postDelayed
                }
                LogUtils.d("onValueChange ---- isSliderTouching = $isSliderTouching ")
                if (isSliderTouching) {
                    return@postDelayed
                }
                when (slider.id) {
                    R.id.slider_star_num -> {
                        engineHandler.starCount = value.toInt()
                        engineHandler.restart()
                    }
                    R.id.slider_speed -> {
                        engineHandler.speed = value
                        engineHandler.restart()
                    }
                }
                engineHandler.restart()
            }
        }, 200)


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

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStartTrackingTouch(slider: Slider) {
        LogUtils.d("onStartTrackingTouch ---- ")
        isSliderTouching = true

    }

    override fun onStopTrackingTouch(slider: Slider) {
        isSliderTouching = false
    }


}