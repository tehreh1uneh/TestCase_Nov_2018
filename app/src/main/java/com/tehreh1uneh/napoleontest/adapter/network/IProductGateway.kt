package com.tehreh1uneh.napoleontest.adapter.network

import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.Offer
import kotlinx.coroutines.Deferred

interface IProductGateway {

    /**
     * @return List of [Banner]s
     */
    suspend fun getBanners(): Deferred<List<Banner>>

    /**
     * @return List of [Offer]s
     */
    suspend fun getOffers(): Deferred<List<Offer>>
}
