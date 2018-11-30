package com.tehreh1uneh.napoleontest.framework.ui.recycler

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Decorator based on vertical and horizontal offsets between items of a RecyclerView
 */
class ItemOffsetDecoration(
    context: Context,
    @DimenRes verticalOffset: Int,
    @DimenRes horizontalOffset: Int
) : RecyclerView.ItemDecoration() {

    private var horizontalOffset = context.resources.getDimensionPixelSize(horizontalOffset)
    private var verticalOffset = context.resources.getDimensionPixelSize(verticalOffset)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(horizontalOffset, verticalOffset, horizontalOffset, verticalOffset)
    }
}
