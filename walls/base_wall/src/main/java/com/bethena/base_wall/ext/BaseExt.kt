package com.bethena.base_wall.ext

import android.view.View
import com.bethena.base_wall.BaseConfigFragment
import com.bethena.base_wall.BaseEngineHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun BaseConfigFragment<BaseEngineHandler>.initDrawerView(
    rootView: View,
    viewId: Int,
    callback: BottomSheetBehavior.BottomSheetCallback
): Pair<View, BottomSheetBehavior<View>> {
    val bottomDrawer: View = rootView.findViewById(viewId)
    var bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
    bottomDrawerBehavior.setUpdateImportantForAccessibilityOnSiblings(true)
    bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    bottomDrawerBehavior.addBottomSheetCallback(callback)
    return Pair(bottomDrawer, bottomDrawerBehavior)
}