package com.tehreh1uneh.napoleontest.framework.ui.recycler

/**
 * Describe functionality of RecyclerView.Adapter
 */
interface IBaseRecyclerAdapter<T: IBaseRecyclerItem>{

    /**
     * Add item to the end of a list
     */
    fun add(item: T)

    /**
     * Add several items to the end of a list
     */
    fun add(items: List<T>)

    /**
     * Add item to concrete position of a list
     */
    fun addTo(position: Int = 0, item: T)

    /**
     * Remove item from position
     */
    fun remove(position: Int)

    /**
     * Remove all items from list
     */
    fun clear()

    /**
     * Update a list of items
     *
     * @param items New content of a list
     */
    fun update(items: List<T>)
}
