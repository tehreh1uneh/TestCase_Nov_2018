package com.tehreh1uneh.napoleontest.entity

import com.google.gson.annotations.SerializedName

/**
 * Model of a banner
 */
data class Banner(
    val id: String,
    val title: String?,
    val desc: String?,
    @field:SerializedName("image")
    val imageUrl: String?
)
