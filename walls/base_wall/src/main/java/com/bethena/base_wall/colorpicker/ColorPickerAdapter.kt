package com.bethena.base_wall.colorpicker

import android.content.res.ColorStateList
import android.graphics.Color
import com.bethena.base_wall.R
import com.bethena.base_wall.utils.ColorUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ColorPickerAdapter(data: MutableList<ColorItem>?) :
    BaseQuickAdapter<ColorItem, BaseViewHolder>(R.layout.item_color_picker, data) {

    init {
        addChildClickViewIds(R.id.fab_color)
    }

    override fun convert(holder: BaseViewHolder, item: ColorItem) {
        var fab = holder.getView<FloatingActionButton>(R.id.fab_color)

        fab.backgroundTintList = ColorStateList.valueOf(item.colorValue)

        var isDarkColor = ColorUtil.isDarkColor(item.colorValue)
        if (isDarkColor){
            fab.imageTintList = ColorStateList.valueOf(Color.WHITE)
        }else{
            fab.imageTintList = ColorStateList.valueOf(Color.BLACK)
        }


        if (item.isSelected){
            fab.setImageResource(R.drawable.ic_baseline_check_24)
        }else{
            fab.setImageDrawable(null)
        }




    }



}