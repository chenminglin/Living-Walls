package com.bethena.healingwall.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.AppUtil
import com.bethena.healingwall.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class HomeAdapter() : BaseQuickAdapter<WallInfo, BaseViewHolder>(R.layout.item_walls) {

    override fun convert(holder: BaseViewHolder, item: WallInfo) {
        if (item.coverBitmap == null) {
            item.coverBitmap = AppUtil.getBitmapFromAsset(context, item.assetCover)
        }
        holder.setText(R.id.tv_title, item.name)
        holder.setImageBitmap(R.id.iv_walls_cover, item.coverBitmap)

    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        data.forEach {
            if (it.coverBitmap!=null){
                if (!it.coverBitmap!!.isRecycled){
                    it.coverBitmap!!.recycle()
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

    }
}