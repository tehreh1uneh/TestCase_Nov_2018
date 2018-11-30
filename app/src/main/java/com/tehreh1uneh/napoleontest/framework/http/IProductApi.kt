package com.tehreh1uneh.napoleontest.framework.http

import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.Offer

/**
 * Input port for API that provides [Banner]s and [Offer]s
 */
interface IProductApi {

    /**
     * @return List of all banners
     */
    suspend fun getBanners(): List<Banner>

    /**
     * @return List of all offers
     */
    suspend fun getOffers(): List<Offer>
}
