package com.tehreh1uneh.napoleontest.entity

import io.reactivex.Scheduler

/**
 * Container for possible RxJava schedulers
 */
interface ISchedulersContainer {
    // UI thread
    val main: Scheduler
    val io: Scheduler
    val computation: Scheduler
    // Single thread computation required!
    val database: Scheduler
}
