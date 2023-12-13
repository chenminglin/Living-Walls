package com.bethena.base_wall.colorpicker

import com.bethena.base_wall.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ColorPickerAdapter(data: MutableList<ColorItem>?) :
    BaseQuickAdapter<ColorItem, BaseViewHolder>(R.layout.item_color_picker, data) {

    init {
        addChildClickViewIds(R.id.fab_color)
    }

    override fun convert(holder: BaseViewHolder, item: ColorItem) {
        var fab = holder.getView<ColorCircleView>(R.id.fab_color)

        fab.setViewColor(item.colorValue)
        fab.isCheck = item.isSelected

    }
}