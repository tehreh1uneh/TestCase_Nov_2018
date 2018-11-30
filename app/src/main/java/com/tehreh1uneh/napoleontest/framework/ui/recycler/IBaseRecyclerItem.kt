package com.tehreh1uneh.napoleontest.framework.ui.recycler

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil

/**
 * Container for item of recycler view
 */
interface IBaseRecyclerItem {

    /**
     * Get id of a view associated with this item
     */
    @LayoutRes
    fun getLayoutId(): Int = 0

    /**
     * @see DiffUtil.Callback.areItemsTheSame
     */
    fun isTheSame(other: IBaseRecyclerItem): Boolean = false

    /**
     * @see DiffUtil.Callback.areContentsTheSame
     */
    fun isContentTheSame(other: IBaseRecyclerItem): Boolean = false
}
