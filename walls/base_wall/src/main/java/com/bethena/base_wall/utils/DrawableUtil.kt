package com.bethena.base_wall.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter

object DrawableUtil {

    fun getDrawableToBitmap(context: Context?, drawableId: Int, colorValue: Int): Bitmap? {
        var bitmap: Bitmap? = null
        context?.let {
            val drawable = it.getDrawable(drawableId)
            if (drawable != null) {
                if (colorValue != 0) {
                    drawable.setColorFilter(
                        PorterDuffColorFilter(
                            colorValue,
                            PorterDuff.Mode.SRC_IN
                        )
                    )
                }

                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                var canvas = Canvas(bitmap!!)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
        return bitmap
    }

    fun getDrawableToBitmap(
        context: Context?,
        drawableId: Int,
        colorValue: Int,
        scale: Float
    ): Bitmap? {
        var bitmap: Bitmap? = null
        context?.let {
            val drawable = it.getDrawable(drawableId)
            if (drawable != null) {
                if (colorValue != 0) {
                    drawable.setColorFilter(
                        PorterDuffColorFilter(
                            colorValue,
                            PorterDuff.Mode.SRC_IN
                        )
                    )
                }

                bitmap = Bitmap.createBitmap(
                    (drawable.intrinsicWidth * scale).toInt(),
                    (drawable.intrinsicHeight * scale).toInt(),
                    Bitmap.Config.ARGB_8888
                )
                var canvas = Canvas(bitmap!!)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
        return bitmap
    }
}