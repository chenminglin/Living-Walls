package com.bethena.base_wall.colorpicker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.bethena.base_wall.R
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.DrawableUtil
import com.bethena.base_wall.utils.ScreenUtil

/**
 * select color view
 */
class ColorCircleView : View {

    private var viewColor: Int = Color.RED
    var isCheck: Boolean = false
    private var borderColor: Int = Color.WHITE
    private var paint = Paint()
    private var checkBitmap: Bitmap? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ColorCircleView, defStyle, 0
        )
        viewColor = a.getColor(R.styleable.ColorCircleView_viewColor, viewColor)
        borderColor = ContextCompat.getColor(context, R.color.md_theme_light_onBackground)

        a.recycle()

//        checkBitmap = DrawableUtil.getDrawableToBitmap(context, R.drawable.ic_baseline_check_24)

        paint.isAntiAlias = true
        paint.strokeWidth = ScreenUtil.dp2pxF(context, 0.5f)
    }

    fun setViewColor(@ColorInt viewColor: Int) {
        this.viewColor = viewColor
//        var isDarkColor = ColorUtil.isDarkColor(viewColor)
//        if (isDarkColor) {
//            checkBitmap = DrawableUtil.getDrawableToBitmap(
//                context,
//                R.drawable.ic_baseline_check_24,
//                Color.WHITE
//            )
//        } else {
//            checkBitmap = DrawableUtil.getDrawableToBitmap(
//                context,
//                R.drawable.ic_baseline_check_24,
//                Color.BLACK
//            )
//        }

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var cx = canvas.width / 2f
        var cy = canvas.height / 2f
        var radius = if (cx <= cy) {
            cx
        } else {
            cy
        }

        paint.color = viewColor
        paint.style = Paint.Style.FILL

        canvas.drawCircle(cx, cy, radius, paint)
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        radius -= paint.strokeWidth / 2
        canvas.drawCircle(cx, cy, radius, paint)
//        checkBitmap?.let {
//            canvas.drawBitmap(it, cx - it.width, cy - it.height, paint)
//        }

    }
}