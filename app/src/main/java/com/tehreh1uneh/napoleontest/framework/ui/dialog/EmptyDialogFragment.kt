package com.tehreh1uneh.napoleontest.framework.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tehreh1uneh.napoleontest.R

/**
 * DialogFragment with empty view. Just modal banner
 */
class EmptyDialogFragment : BasicDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.dialog_fragment_empty, container, false)
        // Some actions
        return rootView
    }
}
