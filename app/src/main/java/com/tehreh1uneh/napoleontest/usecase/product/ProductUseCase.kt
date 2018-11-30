package com.tehreh1uneh.napoleontest.usecase.product

import com.tehreh1uneh.napoleontest.adapter.network.IProductGateway
import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.Offer
import timber.log.Timber

/**
 * @see IProductUseCase
 */
class ProductUseCase(private val productGateway: IProductGateway) : IProductUseCase {

    override suspend fun getBanners(startPosition: Int, size: Int): List<Banner> {
        return try {
            productGateway.getBanners()
                .await()
                // In a real case you need to send new request with a specified size of the page
                .let {
                    if (startPosition > it.lastIndex) return listOf()
                    if (startPosition + size >= it.size) return it.slice(startPosition..it.lastIndex)
                    return it.slice(startPosition until startPosition + size)
                }
        } catch (th: Throwable) {
            Timber.e(th)
            listOf()
        }
    }

    override suspend fun getOffers(): List<Offer> {
        return try {
            productGateway.getOffers().await()
        } catch (th: Throwable) {
            Timber.e(th)
            listOf()
        }
    }
}
