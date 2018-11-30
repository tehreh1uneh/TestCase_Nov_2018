package com.tehreh1uneh.napoleontest.framework.rx

import com.tehreh1uneh.napoleontest.entity.ISchedulersContainer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @see ISchedulersContainer
 */
class SchedulersContainer : ISchedulersContainer {
    override val main: Scheduler = AndroidSchedulers.mainThread()
    override val io: Scheduler = Schedulers.io()
    override val computation: Scheduler = Schedulers.computation()
    override val database: Scheduler = Schedulers.single()
}
