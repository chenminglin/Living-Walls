package com.bethena.living_walls.ui.home

import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.AppUtil
import com.bethena.living_walls.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class HomeAdapter() : BaseQuickAdapter<WallInfo, BaseViewHolder>(R.layout.item_walls) {

    override fun convert(holder: BaseViewHolder, item: WallInfo) {
        var coverBitmap = AppUtil.getBitmapFromAsset(context, item.assetCover)
        holder.setText(R.id.tv_title, item.name)
        holder.setImageBitmap(R.id.iv_walls_cover, coverBitmap)
    }
}