package com.tehreh1uneh.napoleontest.framework.coroutines

import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.entity.ISchedulersContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * @see IDispatchersContainer
 */
class DispatchersContainer (schedulers: ISchedulersContainer) : IDispatchersContainer {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val computation: CoroutineDispatcher = Dispatchers.Default
    override val database: CoroutineDispatcher = schedulers.database.asCoroutineDispatcher()
}
