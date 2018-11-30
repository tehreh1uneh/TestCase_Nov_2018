package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.tehreh1uneh.napoleontest.R
import com.tehreh1uneh.napoleontest.di.AppKodeinAware
import com.tehreh1uneh.napoleontest.framework.ui.IImageLoader
import com.tehreh1uneh.napoleontest.framework.ui.paging.BasePagedListAdapter
import kotlinx.android.synthetic.main.item_banner.view.*
import org.kodein.di.generic.instance

/**
 * RecyclerView adapter based on `Paging library` (Android Architecture components)
 */
class BannersPagedListAdapter : BasePagedListAdapter<BannerRecyclerItem>(), AppKodeinAware {

    private val imageLoader: IImageLoader by instance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BannerViewHolder(inflate(parent.context, viewType, parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BannerViewHolder).bind(getItem(position) ?: return)
    }

    private inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BannerRecyclerItem) {
            val url = item.banner.imageUrl
            if (url == null) {
                itemView.ivBackground.setImageResource(android.R.color.transparent)
            } else {
                imageLoader.loadTo(url, R.drawable.ic_image, itemView.ivBackground)
            }

            itemView.tvTitle.text = item.banner.title
            itemView.tvDescription.text = item.banner.desc
        }
    }
}
