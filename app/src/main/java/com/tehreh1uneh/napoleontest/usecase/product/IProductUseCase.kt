package com.tehreh1uneh.napoleontest.usecase.product

import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.Offer

/**
 * UseCase for interaction with product entities, like [Banner]s and [Offer]s
 */
interface IProductUseCase {

    /**
     * @return List of [Banner]s from [startPosition] (inclusively) to [startPosition]+[size] position (exclusively)
     */
    suspend fun getBanners(startPosition: Int, size: Int): List<Banner>

    /**
     * @return List of all available offers
     */
    suspend fun getOffers(): List<Offer>
}
