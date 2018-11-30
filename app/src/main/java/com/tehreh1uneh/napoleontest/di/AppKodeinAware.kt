package com.tehreh1uneh.napoleontest.di

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/**
 * Wrapper for [KodeinAware] interface that uses dependencies of application level
 */
interface AppKodeinAware : KodeinAware {
    override val kodein: Kodein
        get() = KodeinController.appKodein
}
