package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import com.tehreh1uneh.napoleontest.entity.Offer
import com.tehreh1uneh.napoleontest.framework.ui.recycler.IBaseRecyclerItem

/**
 * Item of a recycler view with offers
 */
data class OfferRecyclerItem(val offer: Offer? = null, val group: String? = null) : IBaseRecyclerItem {

    override fun isTheSame(other: IBaseRecyclerItem): Boolean {
        val otherItem = other as? OfferRecyclerItem
        return offer?.id == otherItem?.offer?.id && group == otherItem?.group
    }

    override fun isContentTheSame(other: IBaseRecyclerItem): Boolean =
        this == (other as? OfferRecyclerItem)
}
