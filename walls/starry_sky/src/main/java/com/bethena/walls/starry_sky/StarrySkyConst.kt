package com.bethena.walls.starry_sky

import android.graphics.Color

object StarrySkyConst {
    val BASE = "key_starry_sky"
    val KEY_STARS_COUNT = "${BASE}_stars_count"
    val KEY_STARS_SPEED = "${BASE}_stars_speed"
    val KEY_BACKGROUND_COLOR = "${BASE}_background_color"
    val KEY_STAR_COLOR = "${BASE}_star_color"
    val KEY_MASH_PERCENT = "${BASE}_mash"
    //一开始没有设置有多少个星星
    val KEY_INIT_STARS_COUNT = 30
    //一开始没有设置的速度
    val KEY_INIT_STARS_SPEED = 1f
    val KEY_INIT_MASH = 0
    val KEY_INIT_MASH_COLOR = Color.parseColor("#00ffffff")
}