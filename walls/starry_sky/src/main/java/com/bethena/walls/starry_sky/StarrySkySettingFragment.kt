package com.bethena.walls.starry_sky

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.SurfaceHolder.Callback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.BaseFragment
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.colorpicker.ColorItem
import com.bethena.base_wall.colorpicker.ColorPickerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider


class StarrySkySettingFragment : BaseFragment(), Slider.OnChangeListener, Slider.OnSliderTouchListener {

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
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_starry_sky_setting
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<View>()
        surfaceView = view.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                engineHandler.create(holder)
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
        inflater.inflate(R.menu.menu_config_starry_sky, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config -> {
                showSettingSheet()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSettingSheet(){
        var bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.fragment_starry_sky_bottom)
        var slider_star_num = bottomSheetDialog.findViewById<Slider>(R.id.slider_star_num)
        var slider_speed = bottomSheetDialog.findViewById<Slider>(R.id.slider_speed)
        var slider_mash = bottomSheetDialog.findViewById<Slider>(R.id.slider_mash)
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
            it.setLabelFormatter { value ->
                "${value}x"
            }
            it.addOnChangeListener(this@StarrySkySettingFragment)
            it.addOnSliderTouchListener(this@StarrySkySettingFragment)
        }
        slider_mash?.let {
            var value = engineHandler.spUtils?.getInt(
                StarrySkyConst.KEY_MASH_PERCENT, StarrySkyConst.KEY_INIT_MASH
            )
            it.value = value!!.toFloat()
            it.setLabelFormatter { value ->
                "${value}%"
            }
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

            slider_mash?.let {
                engineHandler.spUtils?.putInt(
                    StarrySkyConst.KEY_MASH_PERCENT,
                    it.value.toInt()
                )

                it.removeOnChangeListener(this)
                it.removeOnSliderTouchListener(this)
            }
        }

        var rv_background_color_picker =
            bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_background_color_picker)
        var colorItems = arrayListOf<ColorItem>()
        engineHandler.backgroundColors.forEach {
            colorItems.add(ColorItem(it, it == engineHandler.backgroundColor))
        }
        var backgroundColorAdapter = ColorPickerAdapter(colorItems)
        backgroundColorAdapter.setOnItemChildClickListener { adapter, view, position ->
            for (colorItem in colorItems) {
                colorItem.isSelected = colorItem == colorItems[position]
            }
            adapter.notifyDataSetChanged()
            engineHandler.backgroundColor = colorItems[position].colorValue
            engineHandler.spUtils?.putInt(
                StarrySkyConst.KEY_BACKGROUND_COLOR, engineHandler.backgroundColor
            )
        }
        rv_background_color_picker?.adapter = backgroundColorAdapter

        var rv_star_color_picker =
            bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_star_color_picker)
        var starColorItems = arrayListOf<ColorItem>()
        engineHandler.starColors.forEach {
            starColorItems.add(ColorItem(it, it == engineHandler.starColor))
        }
        var starColorAdapter = ColorPickerAdapter(starColorItems)
        starColorAdapter.setOnItemChildClickListener { adapter, view, position ->
            for (colorItem in starColorItems) {
                colorItem.isSelected = colorItem == starColorItems[position]
            }
            adapter.notifyDataSetChanged()
            engineHandler.starColor = starColorItems[position].colorValue
            engineHandler.spUtils?.putInt(
                StarrySkyConst.KEY_STAR_COLOR, engineHandler.starColor
            )
            engineHandler.restart()
        }
        rv_star_color_picker?.adapter = starColorAdapter
        bottomSheetDialog.show()
    }


    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        view?.postDelayed({
            activity?.let {
                if (!fromUser) {
                    return@postDelayed
                }
                LogUtil.d("onValueChange ---- isSliderTouching = $isSliderTouching ")
                if ((slider.id == R.id.slider_star_num
                            || slider.id == R.id.slider_speed) && isSliderTouching
                ) {
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
                    R.id.slider_mash -> {

                        engineHandler.mashColor = ColorUtil.adjustAlpha(Color.BLACK, value / 100)
                    }
                }

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
        LogUtil.d("onStartTrackingTouch ---- ")
        isSliderTouching = true

    }

    override fun onStopTrackingTouch(slider: Slider) {
        isSliderTouching = false
    }


}