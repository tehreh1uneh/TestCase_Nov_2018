package com.tehreh1uneh.napoleontest.framework.logging

import android.util.Log
import timber.log.Timber

class TimberTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean = when (priority) {
        Log.VERBOSE, Log.DEBUG, Log.INFO -> false
        else -> true
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // TODO: Log exceptions to Crashlytics
    }
}
