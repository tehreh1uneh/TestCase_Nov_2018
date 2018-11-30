package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import com.tehreh1uneh.napoleontest.R
import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.framework.ui.recycler.IBaseRecyclerItem

/**
 * Item of a recycler view with banners
 */
data class BannerRecyclerItem(val banner: Banner) : IBaseRecyclerItem {

    override fun getLayoutId(): Int = R.layout.item_banner

    override fun isTheSame(other: IBaseRecyclerItem): Boolean =
        banner.id == (other as? BannerRecyclerItem)?.banner?.id

    override fun isContentTheSame(other: IBaseRecyclerItem): Boolean =
        banner == (other as? BannerRecyclerItem)?.banner
}
