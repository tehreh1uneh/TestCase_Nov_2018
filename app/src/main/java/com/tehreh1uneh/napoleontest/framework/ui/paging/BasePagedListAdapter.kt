package com.tehreh1uneh.napoleontest.framework.ui.paging

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tehreh1uneh.napoleontest.framework.ui.recycler.IBaseRecyclerItem

/**
 * Basic functionality of [PagedListAdapter] including [DiffUtil.ItemCallback] implementation
 */
abstract class BasePagedListAdapter<T : IBaseRecyclerItem> :
        PagedListAdapter<T, RecyclerView.ViewHolder>(BaseItemDiffUtilCallback()) {

    //<editor-fold desc="RecyclerView">
    override fun getItemViewType(position: Int): Int = getItem(position)?.getLayoutId() ?: 0
    //</editor-fold>

    protected fun inflate(context: Context?, @LayoutRes viewType: Int, parent: ViewGroup): View =
            LayoutInflater.from(context).inflate(viewType, parent, false)

    private class BaseItemDiffUtilCallback<T : IBaseRecyclerItem> : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T?, newItem: T?): Boolean {
            if (oldItem == null || newItem == null) return false
            return oldItem.isTheSame(newItem)
        }

        override fun areContentsTheSame(oldItem: T?, newItem: T?): Boolean {
            if (oldItem == null || newItem == null) return false
            return oldItem.isContentTheSame(newItem)
        }
    }
}
