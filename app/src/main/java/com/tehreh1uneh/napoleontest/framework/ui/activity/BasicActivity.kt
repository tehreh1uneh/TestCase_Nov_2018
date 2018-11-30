package com.tehreh1uneh.napoleontest.framework.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.tehreh1uneh.napoleontest.framework.ui.state.InstanceStateProvider
import com.tehreh1uneh.napoleontest.framework.ui.state.SAVABLE_STATE_DEFAULT_NAME
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Basic functionality of Activity
 */
@SuppressLint("Registered")
open class BasicActivity : MvpAppCompatActivity() {

    protected lateinit var rootView: View
    private val bundle: Bundle = Bundle()
    private val compositeDisposable = CompositeDisposable()

    //<editor-fold desc="Activity">
    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.getBundle(SAVABLE_STATE_DEFAULT_NAME)?.let { bundle.putAll(it) }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle(SAVABLE_STATE_DEFAULT_NAME, bundle)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
    //</editor-fold>

    //<editor-fold desc="Property delegation">
    protected fun <T> instanceState() = InstanceStateProvider.Nullable<T>(bundle)

    protected fun <T> instanceState(defaultValue: T) = InstanceStateProvider.NotNull(bundle, defaultValue)
    //</editor-fold>

    protected fun Disposable.toComposite() {
        compositeDisposable.add(this)
    }
}
