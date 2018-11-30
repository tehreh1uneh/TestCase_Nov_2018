package com.tehreh1uneh.napoleontest.framework.ui.dialog

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.tehreh1uneh.napoleontest.framework.ui.state.InstanceStateProvider
import com.tehreh1uneh.napoleontest.framework.ui.state.SAVABLE_STATE_DEFAULT_NAME
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Basic functionality of DialogFragments
 */
open class BasicDialogFragment : MvpAppCompatDialogFragment() {

    protected lateinit var rootView: View
    protected val parent: Any?
        get() = parentFragment ?: activity
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val bundle: Bundle = Bundle()

    //<editor-fold desc="Fragment">
    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.getBundle(SAVABLE_STATE_DEFAULT_NAME)?.let { bundle.putAll(it) }
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        beforeSaveState()
        (arguments ?: Bundle()).putBundle(SAVABLE_STATE_DEFAULT_NAME, bundle)
        super.onPause()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onDestroyView() {
        /* Do not dismiss dialog (https://issuetracker.google.com/issues/36929400) */
        if (retainInstance) dialog?.setDismissMessage(null)
        super.onDestroyView()
    }
    //</editor-fold>

    //<editor-fold desc="Property delegation">
    protected fun <T> instanceState() = InstanceStateProvider.Nullable<T>(bundle)

    protected fun <T> instanceState(defaultValue: T) = InstanceStateProvider.NotNull(bundle, defaultValue)

    /**
     * Called before saving data to [bundle]
     */
    protected open fun beforeSaveState() {}
    //</editor-fold>

    protected fun Disposable.toComposite() {
        compositeDisposable.add(this)
    }

}
