package com.tehreh1uneh.napoleontest.framework.ui.recycler

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Base functionality of [RecyclerView.Adapter] including [DiffUtil.Callback]
 * View type is a link to layout resource
 */
abstract class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    IBaseRecyclerAdapter<IBaseRecyclerItem> {

    protected val items: MutableList<IBaseRecyclerItem> = mutableListOf()

    //<editor-fold desc="IBaseRecyclerAdapter">
    override fun add(item: IBaseRecyclerItem) {
        items += item
        notifyItemInserted(items.lastIndex)
    }

    override fun add(items: List<IBaseRecyclerItem>) {

        if (items.isEmpty()) return

        val startPosition = this.items.lastIndex + 1
        for (item in items) {
            this.items += item
        }
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun addTo(position: Int, item: IBaseRecyclerItem) {
        items.add(position, item)
        notifyItemChanged(position)
    }

    override fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun clear() {
        val removedSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, removedSize)
    }

    override fun update(items: List<IBaseRecyclerItem>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffUtilCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
    //</editor-fold>

    //<editor-fold desc="RecyclerView">
    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()
    //</editor-fold>

    protected fun inflate(context: Context?, @LayoutRes viewType: Int, parent: ViewGroup): View =
        LayoutInflater.from(context).inflate(viewType, parent, false)

    private class BaseDiffUtilCallback(
        val oldList: List<IBaseRecyclerItem>,
        val newList: List<IBaseRecyclerItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].isTheSame(newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].isContentTheSame(newList[newItemPosition])

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}
