package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import android.arch.paging.PositionalDataSource
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.usecase.product.IProductUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Data source for paged list of banners
 */
class BannersPositionalDataSource(
    private val dispatchers: IDispatchersContainer,
    private val productUseCase: IProductUseCase
) : PositionalDataSource<BannerRecyclerItem>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<BannerRecyclerItem>) {
        // It's important to retrieve result asynchronously, because initial call is synchronous
        GlobalScope.launch(dispatchers.io) {
            callback.onResult(
                getResult(params.requestedStartPosition, params.requestedLoadSize),
                params.requestedStartPosition
            )
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<BannerRecyclerItem>) {
        GlobalScope.launch {
            callback.onResult(getResult(params.startPosition, params.loadSize))
        }
    }

    private suspend fun getResult(start: Int, size: Int): List<BannerRecyclerItem> =
        productUseCase.getBanners(start, size).map { BannerRecyclerItem(it) }

}
