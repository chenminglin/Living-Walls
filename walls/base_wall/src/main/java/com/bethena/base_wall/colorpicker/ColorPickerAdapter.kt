package com.bethena.base_wall.colorpicker

import android.content.res.ColorStateList
import androidx.core.graphics.ColorUtils
import com.bethena.base_wall.R
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

        if (item.isSelected){
            fab.setImageResource(R.drawable.ic_baseline_check_24)
        }else{
            fab.setImageDrawable(null)
        }

        

    }



}