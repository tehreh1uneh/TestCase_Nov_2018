package com.tehreh1uneh.napoleontest.adapter.ui.product

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.framework.ui.activity.product.OfferRecyclerItem
import com.tehreh1uneh.napoleontest.usecase.product.IProductUseCase
import java.util.concurrent.Executor

/**
 * Moxy input port for CategoriesActivity
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface CategoriesViewPort : MvpView {

    /**
     * Update list of banners
     */
    fun updateBanners(dispatchers: IDispatchersContainer, mainExecutor: Executor, productUseCase: IProductUseCase)

    /**
     * Update list of offers
     */
    fun updateOffers(offers: List<OfferRecyclerItem>)
}
