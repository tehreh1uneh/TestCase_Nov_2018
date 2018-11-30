package com.tehreh1uneh.napoleontest.framework.ui

import android.support.annotation.DrawableRes
import android.widget.ImageView

/**
 * Input port for uploading images to ImageView by its URLs
 */
interface IImageLoader {

    /**
     * Load an image from [url] to [view] and use passed [placeholder] until the image is loading
     */
    fun loadTo(url: String, @DrawableRes placeholder: Int, view: ImageView)

    /**
     * Load an image (and round its corners) from [url] to [view] and use passed [placeholder] until the image is loading.
     */
    fun loadRoundedTo(url: String, @DrawableRes placeholder: Int, view: ImageView)
}
