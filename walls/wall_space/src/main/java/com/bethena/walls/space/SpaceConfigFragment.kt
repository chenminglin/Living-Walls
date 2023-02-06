package com.bethena.walls.space

import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceHolder
import android.view.View
import com.bethena.base_wall.BaseConfigFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback


class SpaceConfigFragment : BaseConfigFragment<SpaceEngineHandler>() {
    var bottomDrawerBehavior: BottomSheetBehavior<View>? = null
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
        var bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomDrawerBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config_color -> {
                bottomDrawerBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
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
        return false
    }


}