package com.tehreh1uneh.napoleontest.framework.ui.activity.product

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.queryTextChanges
import com.tehreh1uneh.napoleontest.R
import com.tehreh1uneh.napoleontest.adapter.ui.product.CategoriesPresenter
import com.tehreh1uneh.napoleontest.adapter.ui.product.CategoriesViewPort
import com.tehreh1uneh.napoleontest.di.AppKodeinAware
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.entity.ISchedulersContainer
import com.tehreh1uneh.napoleontest.framework.ui.activity.BasicActivity
import com.tehreh1uneh.napoleontest.framework.ui.dialog.EmptyDialogFragment
import com.tehreh1uneh.napoleontest.framework.ui.ext.findRoot
import com.tehreh1uneh.napoleontest.framework.ui.recycler.ItemOffsetDecoration
import com.tehreh1uneh.napoleontest.usecase.product.IProductUseCase
import kotlinx.android.synthetic.main.activity_categories.view.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Launcher Activity that contains banners and offers
 * Based on Moxy Activity
 */
class CategoriesActivity : BasicActivity(), CategoriesViewPort, AppKodeinAware {

    @InjectPresenter lateinit var presenter: CategoriesPresenter
    private val bannersAdapter = BannersPagedListAdapter()
    private val offersAdapter = OffersAdapter()
    private val schedulers: ISchedulersContainer by instance()

    //<editor-fold desc="Parent methods">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        rootView = findRoot()
        setSupportActionBar(rootView.toolbar)
        initAdapters()
        initInfoButton()
        initSearch()
    }
    //</editor-fold>

    //<editor-fold desc="CategoryViewPort">
    override fun updateBanners(
        dispatchers: IDispatchersContainer,
        mainExecutor: Executor,
        productUseCase: IProductUseCase
    ) {
        bannersAdapter.submitList(buildPagedList(dispatchers, mainExecutor, productUseCase))
    }

    override fun updateOffers(offers: List<OfferRecyclerItem>) {
        offersAdapter.update(offers)
    }

    //</editor-fold>

    private fun initAdapters() {
        with(rootView.rvBanners) {
            adapter = bannersAdapter
            layoutManager = LinearLayoutManager(
                this@CategoriesActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            addItemDecoration(
                ItemOffsetDecoration(
                    this@CategoriesActivity,
                    R.dimen.item_decoration_vertical_offset,
                    R.dimen.item_decoration_horizontal_offset
                )
            )
            LinearSnapHelper().attachToRecyclerView(this)
        }

        with(rootView.rvOffers) {
            adapter = offersAdapter
            layoutManager = LinearLayoutManager(this@CategoriesActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@CategoriesActivity, LinearLayoutManager.VERTICAL))
            ViewCompat.setNestedScrollingEnabled(this, false)
        }
    }

    private fun buildPagedList(
        dispatchers: IDispatchersContainer,
        mainExecutor: Executor,
        productUseCase: IProductUseCase
    ): PagedList<BannerRecyclerItem> {
        val dataSource = BannersPositionalDataSource(dispatchers, productUseCase)

        return PagedList.Builder(dataSource, PAGED_LIST_CONFIG)
            .setNotifyExecutor(mainExecutor)
            .setFetchExecutor(Executors.newCachedThreadPool())
            .build()
    }

    private fun initInfoButton() {
        rootView.ivInfo.setOnClickListener {
            EmptyDialogFragment().show(supportFragmentManager, "")
        }
    }

    private fun initSearch() {
        rootView.svSearch.queryTextChanges()
            .skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .distinctUntilChanged()
            .observeOn(schedulers.main)
            .subscribe(presenter::onNewQueryString, Timber::e)
            .toComposite()
    }

    companion object {
        private const val BANNERS_PAGE_SIZE = 10

        private val PAGED_LIST_CONFIG = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(BANNERS_PAGE_SIZE)
            .build()
    }
}
