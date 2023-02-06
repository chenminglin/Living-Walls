package com.bethena.walls.space

import android.content.Context
import androidx.core.content.ContextCompat

class SpaceColor {

    var pagkageName: String

    var bg_color_start = 0
    var bg_color_center = 0
    var star_color1 = 0
    var star_color2 = 0
    var planet1_color1 = 0
    var planet1_color2 = 0
    var planet2_color1 = 0
    var planet2_color2 = 0
    var planet3_color1 = 0
    var planet3_color2 = 0
    var planet3_ring_color = 0
    var big_planet_color1 = 0
    var big_planet_color2 = 0
    var big_planet_pit_color = 0
    var big_planet_ring_color = 0
    var bp_sub1_color1 = 0
    var bp_sub1_color2 = 0
    var bp_sub2_color1 = 0
    var bp_sub2_color2 = 0
    var meteor_color = 0

    constructor(context: Context, tempId: String) {
        pagkageName = context.packageName
        initAllColor(context, tempId)
    }

    private fun initAllColor(context: Context, tempId: String) {
        bg_color_start = initColor(context, "ws_${tempId}_bg_color_start")
        bg_color_center = initColor(context, "ws_${tempId}_bg_color_center")
        star_color1 = initColor(context, "ws_${tempId}_star_color1")
        star_color2 = initColor(context, "ws_${tempId}_star_color2")
        planet1_color1 = initColor(context, "ws_${tempId}_planet1_color1")
        planet1_color2 = initColor(context, "ws_${tempId}_planet1_color2")
        planet2_color1 = initColor(context, "ws_${tempId}_planet2_color1")
        planet2_color2 = initColor(context, "ws_${tempId}_planet2_color2")
        planet3_color1 = initColor(context, "ws_${tempId}_planet3_color1")
        planet3_color2 = initColor(context, "ws_${tempId}_planet3_color2")
        planet3_ring_color = initColor(context, "ws_${tempId}_planet3_ring_color")
        big_planet_color1 = initColor(context, "ws_${tempId}_big_planet_color1")
        big_planet_color2 = initColor(context, "ws_${tempId}_big_planet_color2")
        big_planet_pit_color = initColor(context, "ws_${tempId}_big_planet_pit_color")
        big_planet_ring_color = initColor(context, "ws_${tempId}_big_planet_ring_color")
        bp_sub1_color1 = initColor(context, "ws_${tempId}_bp_sub1_color1")
        bp_sub1_color2 = initColor(context, "ws_${tempId}_bp_sub1_color2")
        bp_sub2_color1 = initColor(context, "ws_${tempId}_bp_sub2_color1")
        bp_sub2_color2 = initColor(context, "ws_${tempId}_bp_sub2_color2")
        meteor_color = initColor(context, "ws_${tempId}_meteor_color")
    }


    private fun initColor(context: Context, name: String): Int {
        var colorResId = context.resources.getIdentifier(name, "color", pagkageName)
        return ContextCompat.getColor(context, colorResId)
    }


}