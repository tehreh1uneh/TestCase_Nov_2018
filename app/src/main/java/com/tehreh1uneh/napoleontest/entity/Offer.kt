package com.tehreh1uneh.napoleontest.entity

import com.google.gson.annotations.SerializedName

/**
 * Model of an offer
 */
data class Offer(
    val id: String,
    val title: String,
    val desc: String?,
    val groupName: String,
    val type: Type,
    @field:SerializedName("image")
    val imageUrl: String?,
    val price: Float?,
    val discount: Float?
) {
    enum class Type {
        @field:SerializedName("product")
        PRODUCT,
        @field:SerializedName("item")
        ITEM
    }
}
