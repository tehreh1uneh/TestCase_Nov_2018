package com.tehreh1uneh.napoleontest.framework.ui.ext

import android.app.Activity
import android.view.View

/**
 * Find a root view of the Activity
 */
fun Activity.findRoot(): View = findViewById(android.R.id.content)
