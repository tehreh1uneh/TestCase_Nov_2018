package com.tehreh1uneh.napoleontest.framework.http

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tehreh1uneh.napoleontest.entity.ApiError
import com.tehreh1uneh.napoleontest.entity.Banner
import com.tehreh1uneh.napoleontest.entity.Offer
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.lang.reflect.Type

/**
 * [IProductApi] implementation based on mock that placed on GitHubGist
 * Ktor uses as HTTP client
 * @see IProductApi
 */
class ProductApi(private val client: HttpClient, private val gson: Gson) : IProductApi {

    private val bannersUrl =
        "https://gist.githubusercontent.com/tehreh1uneh/bfc8df25a52d2d5cf82f608dc7fa3482/raw/cd3072ff304542fc781f5adce5d740dc127eb491/banners.json"

    private val offersUrl =
        "https://gist.githubusercontent.com/tehreh1uneh/acfc16ada0339386ee7597ede8aefe2d/raw/deeb59a0ddf23108889d6f9754df37f82f79501b/offers.json"

    //<editor-fold desc="IProductApi">
    override suspend fun getBanners(): List<Banner> =
        getAndParse(bannersUrl, object : TypeToken<List<Banner>>() {}.type)

    override suspend fun getOffers(): List<Offer> =
        getAndParse(offersUrl, object : TypeToken<List<Offer>>() {}.type)
    //</editor-fold>

    private suspend fun <T> getAndParse(url: String, collectionType: Type): T {
        val response = client.get<String>(url)

        try {
            return gson.fromJson(response, collectionType)
        } catch (th: Throwable) {
            val error = gson.fromJson(response, ApiError::class.java)
            throw NetworkErrorException("${error.code}:${error.message}")
        }
    }
}
