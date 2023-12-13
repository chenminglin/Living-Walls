package com.bethena.walls_test

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnChangeListener

class ColorTestActivity : AppCompatActivity() {
    lateinit var v_color:View
    lateinit var tv_color:TextView
    val hsv = FloatArray(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_test)

        v_color = findViewById<View>(R.id.v_color)
        tv_color = findViewById<TextView>(R.id.tv_color)
        hsv[0] = 10f
        hsv[1] = 0.5f
        hsv[2] = 0.5f
        setColor()

        findViewById<Slider>(R.id.seekBar)
            .addOnChangeListener(object :OnChangeListener{
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                if (fromUser){

                    hsv[0] = value// 色相范围为 0 ~ 360

                    setColor()
                }
            }
        })

        findViewById<Slider>(R.id.seekBar2)
            .addOnChangeListener(object :OnChangeListener{
                override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                    if (fromUser){
                        hsv[1] = value // 饱和度范围为 0.5 ~ 1.0
                        setColor()
                    }
                }
            })

        findViewById<Slider>(R.id.seekBar3)
            .addOnChangeListener(object :OnChangeListener{
                override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                    if (fromUser){
                        hsv[2] = value // 值范围为 0.5 ~ 1.0
                        setColor()

                    }
                }
            })
    }

    fun setColor(){
        var color = Color.HSVToColor(hsv)

        v_color.setBackgroundColor(color)
        tv_color.text = toHexString(color)
    }
    fun toHexString(color: Int): String? {
        return "#" + Integer.toHexString(color).substring(2)
    }
}