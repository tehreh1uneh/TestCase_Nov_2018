package com.tehreh1uneh.napoleontest.entity

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Container for possible coroutine dispatchers
 */
interface IDispatchersContainer {
    // UI thread
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val computation: CoroutineDispatcher
    // Single thread computation required!
    val database: CoroutineDispatcher
}
