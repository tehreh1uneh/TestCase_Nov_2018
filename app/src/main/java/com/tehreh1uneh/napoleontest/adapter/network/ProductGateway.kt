package com.tehreh1uneh.napoleontest.adapter.network

import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.entity.Offer
import com.tehreh1uneh.napoleontest.framework.http.IProductApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * @see IProductGateway
 */
class ProductGateway(private val api: IProductApi, private val dispatchers: IDispatchersContainer) : IProductGateway {

    override suspend fun getBanners(): Deferred<List<Banner>> {
        return GlobalScope.async(dispatchers.io) {
            api.getBanners()
            // TODO: On error read and return cached values
        }
    }

    override suspend fun getOffers(): Deferred<List<Offer>> {
        return GlobalScope.async(dispatchers.io) {
            api.getOffers()
            // TODO: On error read and return cached values
        }
    }
}
