package com.tehreh1uneh.napoleontest.framework.glide

import android.content.Context
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tehreh1uneh.napoleontest.framework.ui.IImageLoader

/**
 * [IImageLoader] implementation based on Glide library
 * @see IImageLoader
 */
class GlideImageLoader (private val context: Context): IImageLoader {

    override fun loadTo(url: String, @DrawableRes placeholder: Int, view: ImageView) {
        GlideApp.with(context).clear(view)

        GlideApp.with(context)
            .load(url)
            .placeholder(placeholder)
            .centerCrop()
            .into(view)
    }

    override fun loadRoundedTo(url: String, placeholder: Int, view: ImageView) {
        GlideApp.with(context).clear(view)

        GlideApp.with(context)
            .load(url)
            .apply(RequestOptions().apply{
                transforms(CenterCrop(), RoundedCorners(8))
            })
            .placeholder(placeholder)
            .into(view)
    }
}
