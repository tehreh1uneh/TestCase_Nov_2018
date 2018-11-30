package com.tehreh1uneh.napoleontest.entity

/**
 * Model of an error request for [API](s3.eu-central-1.amazonaws.com/sl.files/)
 */
data class ApiError(
    val code: Int,
    val message: String
)
