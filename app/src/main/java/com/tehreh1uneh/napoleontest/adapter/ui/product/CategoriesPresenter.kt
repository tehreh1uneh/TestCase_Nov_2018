package com.tehreh1uneh.napoleontest.adapter.ui.product

import com.arellomobile.mvp.InjectViewState
import com.tehreh1uneh.napoleontest.di.AppKodeinAware
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.framework.moxy.BasicMvpPresenter
import com.tehreh1uneh.napoleontest.framework.ui.Application
import com.tehreh1uneh.napoleontest.framework.ui.activity.product.OfferRecyclerItem
import com.tehreh1uneh.napoleontest.usecase.product.IProductUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import java.util.concurrent.Executor

/**
 * Moxy presenter for `CategoriesActivity`
 */
@InjectViewState
class CategoriesPresenter : BasicMvpPresenter<CategoriesViewPort>(), AppKodeinAware {

    private val dispatchers: IDispatchersContainer by instance()
    private val mainExecutor: Executor by instance(Application.TAG_MAIN_EXECUTOR)
    private val productUseCase: IProductUseCase by instance()

    //<editor-fold desc="MvpPresenter">
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateBanners()
        updateOffers()
    }
    //</editor-fold>

    //<editor-fold desc="Input port">
    /**
     * Pass [query] to update CategoriesActivity content
     */
    fun onNewQueryString(query: CharSequence) {
        // TODO: Send new request to API queue, then update UI
    }
    //</editor-fold>

    /**
     * Trigger updating of a list of banners in the main thread
     */
    private fun updateBanners() {
        GlobalScope.launch(dispatchers.main) {
            viewState?.updateBanners(dispatchers, mainExecutor, productUseCase)
        }
    }

    /**
     * Get a list of Offers from `UseCase` layer, map it to applicable form for View
     * and trigger updating of a list of offers in the main thread
     */
    private fun updateOffers() {
        GlobalScope.launch(dispatchers.computation) {
            val offers = productUseCase.getOffers()
            val groups = offers.map { it.groupName }.distinct()
            val offersMap = offers.groupBy { it.groupName }
            val mappedItems: MutableList<OfferRecyclerItem> = mutableListOf()

            for (group in groups) {
                mappedItems.add(OfferRecyclerItem(group = group))
                offersMap[group]?.forEach { mappedItems.add(OfferRecyclerItem(it)) }
            }

            launch(dispatchers.main) {
                viewState?.updateOffers(mappedItems)
            }
        }
    }
}
