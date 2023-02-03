package com.bethena.base_wall

import android.content.Context
import android.graphics.Canvas

abstract class BaseMaterialFactory {
    abstract fun provider(context: Context, canvas: Canvas, ratePer: Float)

    abstract fun doDraw(context: Context, canvas: Canvas)

    abstract fun destory()
}