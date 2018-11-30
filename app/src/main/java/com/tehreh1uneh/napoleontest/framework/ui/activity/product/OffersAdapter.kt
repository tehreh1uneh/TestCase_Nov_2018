package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tehreh1uneh.napoleontest.R
import com.tehreh1uneh.napoleontest.di.AppKodeinAware
import com.tehreh1uneh.napoleontest.entity.Offer
import com.tehreh1uneh.napoleontest.entity.SUM_FORMAT
import com.tehreh1uneh.napoleontest.framework.ui.IImageLoader
import com.tehreh1uneh.napoleontest.framework.ui.recycler.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_offer.view.*
import org.kodein.di.generic.instance

/**
 * Classic RecyclerView adapter for offers
 */
class OffersAdapter : BaseRecyclerAdapter(), AppKodeinAware {

    private val imageLoader: IImageLoader by instance()

    //<editor-fold desc="RecyclerView">
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            R.layout.item_offer_header -> HeaderHolder(inflate(context, viewType, parent))
            R.layout.item_offer_swipable -> OfferHolder(inflate(context, viewType, parent))
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val offerHolder = holder as? ViewHolder ?: return
        val item = items[position] as? OfferRecyclerItem ?: return
        offerHolder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position] as OfferRecyclerItem
        return when {
            item.offer != null -> R.layout.item_offer_swipable
            else -> R.layout.item_offer_header
        }
    }
    //</editor-fold>

    private interface ViewHolder {
        fun bind(item: OfferRecyclerItem)
    }

    private class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ViewHolder {

        override fun bind(item: OfferRecyclerItem) {
            (itemView as? TextView)?.text = item.group
        }
    }

    private inner class OfferHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ViewHolder {

        override fun bind(item: OfferRecyclerItem) {
            recoverInitialVisibility()
            startImageLoading(item.offer?.imageUrl)
            updateDescription(item)
            setPrices(item)
        }

        private fun recoverInitialVisibility() {
            itemView.tvPrice.visibility = View.VISIBLE
            itemView.tvPriceWithDiscount.visibility = View.VISIBLE
            itemView.tvDiscount.visibility = View.VISIBLE
        }

        private fun startImageLoading(url: String?) {
            if (url == null) {
                itemView.ivImage.setImageResource(android.R.color.transparent)
            } else {
                imageLoader.loadRoundedTo(url, R.drawable.ic_image, itemView.ivImage)
            }
        }

        private fun updateDescription(item: OfferRecyclerItem) {
            itemView.tvTitle.text = item.offer?.title
            itemView.tvDescription.text = item.offer?.desc
        }

        private fun setPrices(item: OfferRecyclerItem) {
            if (item.offer?.type == Offer.Type.PRODUCT) {
                val price = item.offer.price?.takeIf { it >= 0 } ?: 0f
                val discount = item.offer.discount

                if (discount != null && discount > 0 && price > 0) {
                    val priceWithDiscount = (price - discount).takeIf { it >= 0f } ?: 0f
                    itemView.tvPriceWithDiscount.text = SUM_FORMAT.format(priceWithDiscount)

                    val percentDiscount = Math.round(discount / price * 100).takeIf { it in 1..100 } ?: 0
                    if (percentDiscount == 0) {
                        itemView.tvDiscount.visibility = View.GONE
                    } else {
                        @SuppressLint("SetTextI18n")
                        itemView.tvDiscount.text = "-$percentDiscount%"
                    }

                    val strikethroughPrice = SpannableString(SUM_FORMAT.format(price))
                    strikethroughPrice.setSpan(
                        StrikethroughSpan(),
                        0,
                        strikethroughPrice.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    itemView.tvPrice.text = strikethroughPrice
                } else {
                    itemView.tvPrice.text = SUM_FORMAT.format(price)
                    itemView.tvPriceWithDiscount.visibility = View.GONE
                    itemView.tvDiscount.visibility = View.GONE
                }
            } else {
                itemView.tvPrice.visibility = View.GONE
                itemView.tvPriceWithDiscount.visibility = View.GONE
                itemView.tvDiscount.visibility = View.GONE
            }
        }
    }
}
