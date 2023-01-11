package com.bethena.base_wall.colorpicker

import android.content.res.ColorStateList
import com.bethena.base_wall.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ColorPickerAdapter(data: MutableList<ColorItem>?) :
    BaseQuickAdapter<ColorItem, BaseViewHolder>(R.layout.item_color_picker, data) {

    override fun convert(holder: BaseViewHolder, item: ColorItem) {
        var fab = holder.getView<FloatingActionButton>(R.id.fab_color)
        fab.backgroundTintList = ColorStateList.valueOf(item.colorValue)

    }

}