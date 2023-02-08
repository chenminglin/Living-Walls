package com.bethena.walls.rainbow

import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceHolder
import android.view.View
import com.bethena.base_wall.BaseConfigFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnChangeListener


class RainbowConfigFragment : BaseConfigFragment<RainbowEngineHandler>() {
    lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>
    override fun menuId(): Int {
        return R.menu.menu_config_rainbow
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
        val bottomDrawer: View = view.findViewById(R.id.c_drawer)
        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomDrawerBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        var slider = view.findViewById<Slider>(R.id.slider_mash)
        var defaultMash = engineHandler.spUtils.getInt(RainbowConst.KEY_MASH_PERCENT, 0)
        initBaseMashSlider(slider, defaultMash.toFloat())

        slider.addOnChangeListener(object : OnChangeListener {
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                if (fromUser) {
                    engineHandler.spUtils.putInt(RainbowConst.KEY_MASH_PERCENT, value.toInt())
                    engineHandler.resetMash()
                }
            }
        })


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config_equalizer -> {
                bottomDrawerBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun inflateId(): Int {
        return R.layout.fragment_rainbow_config
    }

    override fun newEngineHandler(): RainbowEngineHandler {
        return RainbowEngineHandler(requireContext())
    }

    override fun onBackPressed(): Boolean {
        if (bottomDrawerBehavior.state==BottomSheetBehavior.STATE_EXPANDED){
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }
        return false
    }


}