package com.bethena.walls.circle

import android.view.animation.*

object InterpolatorUtil {

    fun getInterpolators():ArrayList<Interpolator>{
        var interpolators = arrayListOf<Interpolator>()

        interpolators.add(LinearInterpolator())
        interpolators.add(OvershootInterpolator())
        interpolators.add(AccelerateDecelerateInterpolator())
        interpolators.add(AccelerateInterpolator())
        interpolators.add(AnticipateInterpolator())
        interpolators.add(AnticipateOvershootInterpolator())
        interpolators.add(BounceInterpolator())
//        interpolators.add(CycleInterpolator(3f))
        interpolators.add(DecelerateInterpolator())

        return interpolators
    }
}