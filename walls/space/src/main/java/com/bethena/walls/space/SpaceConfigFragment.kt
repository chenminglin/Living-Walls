package com.bethena.walls.space

import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceHolder
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.BaseConfigFragment
import com.bethena.base_wall.colorpicker.ColorItem
import com.bethena.base_wall.colorpicker.ColorPickerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.slider.Slider


class SpaceConfigFragment : BaseConfigFragment<SpaceEngineHandler>(), Slider.OnChangeListener {
    private lateinit var bottomDrawerBehaviorColors: BottomSheetBehavior<View>
    private lateinit var bottomDrawerBehaviorQualizer: BottomSheetBehavior<View>
    override fun menuId(): Int {
        return R.menu.menu_config_space
    }

    override fun onSurfaceCreated(view: View, holder: SurfaceHolder) {
        engineHandler.surfaceCreated(holder)
    }

    override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun onSurfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomDrawer: View = view.findViewById(R.id.c_drawer_colors)
        bottomDrawerBehaviorColors = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehaviorColors.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehaviorColors.state = BottomSheetBehavior.STATE_HIDDEN
        bottomDrawerBehaviorColors.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        var recyclerView = view.findViewById<RecyclerView>(R.id.rv_background_color_picker)
        var colors = ArrayList<ColorItem>()
        colors.add(
            ColorItem(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.ws_temp1_big_planet_color1
                ), true
            )
        )
        colors.add(
            ColorItem(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.ws_temp2_big_planet_color2
                ), false
            )
        )
        var adapter = ColorPickerAdapter(colors)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    engineHandler.changeColorTemp(ColorTemp.TEMP1)
                }
                1 -> {
                    engineHandler.changeColorTemp(ColorTemp.TEMP2)
                }
            }
        }
        recyclerView.adapter = adapter
        val c_drawer_qualizer: View = view.findViewById(R.id.c_drawer_qualizer)
        bottomDrawerBehaviorQualizer = BottomSheetBehavior.from(c_drawer_qualizer)
        bottomDrawerBehaviorQualizer.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehaviorQualizer.state = BottomSheetBehavior.STATE_HIDDEN
        bottomDrawerBehaviorQualizer.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        var mashSlider = view.findViewById<Slider>(R.id.slider_mash)
        initBaseMashSlider(mashSlider, engineHandler.getMashValue().toFloat())
        mashSlider.addOnChangeListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config_color -> {
                bottomDrawerBehaviorColors.state = BottomSheetBehavior.STATE_EXPANDED
            }
            R.id.menu_config_equalizer -> {
                bottomDrawerBehaviorQualizer.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun inflateId(): Int {
        return R.layout.fragment_space_config
    }

    override fun newEngineHandler(): SpaceEngineHandler {
        return SpaceEngineHandler(context)
    }

    override fun onBackPressed(): Boolean {
        if (bottomDrawerBehaviorColors.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomDrawerBehaviorColors.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }

        if (bottomDrawerBehaviorQualizer.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomDrawerBehaviorQualizer.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }
        return false
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        if (fromUser) {
            when (slider.id) {
                R.id.slider_mash -> {
                    engineHandler.spUtils.putInt(SpaceConst.KEY_MASH_PERCENT, value.toInt())
                    engineHandler.resetMash()
                }
            }
        }
    }


}