package com.tehreh1uneh.napoleontest.di

import org.kodein.di.Kodein

/**
 * Singleton that contains all possible instances of [Kodein] for different scopes
 */
object KodeinController {

    lateinit var appKodein: Kodein
        private set

    fun initApplicationComponent(kodein: Kodein) {
        appKodein = kodein
    }
}
