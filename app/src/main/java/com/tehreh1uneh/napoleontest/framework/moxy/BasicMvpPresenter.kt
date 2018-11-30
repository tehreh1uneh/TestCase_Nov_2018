package com.tehreh1uneh.napoleontest.framework.moxy

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Basic functionality of [MvpPresenter] (Moxy)
 */
open class BasicMvpPresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun Disposable.toComposite() {
        compositeDisposable.add(this)
    }
}
