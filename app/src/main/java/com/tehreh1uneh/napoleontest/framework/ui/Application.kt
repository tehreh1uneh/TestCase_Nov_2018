package com.tehreh1uneh.napoleontest.framework.ui

import android.os.Handler
import android.os.Looper
import android.support.multidex.MultiDexApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tehreh1uneh.napoleontest.adapter.network.IProductGateway
import com.tehreh1uneh.napoleontest.adapter.network.ProductGateway
import com.tehreh1uneh.napoleontest.di.KodeinController
import com.tehreh1uneh.napoleontest.entity.IDispatchersContainer
import com.tehreh1uneh.napoleontest.entity.ISchedulersContainer
import com.tehreh1uneh.napoleontest.framework.coroutines.DispatchersContainer
import com.tehreh1uneh.napoleontest.framework.glide.GlideImageLoader
import com.tehreh1uneh.napoleontest.framework.http.IProductApi
import com.tehreh1uneh.napoleontest.framework.http.ProductApi
import com.tehreh1uneh.napoleontest.framework.logging.TimberTree
import com.tehreh1uneh.napoleontest.framework.rx.SchedulersContainer
import com.tehreh1uneh.napoleontest.usecase.product.IProductUseCase
import com.tehreh1uneh.napoleontest.usecase.product.ProductUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber
import java.util.concurrent.Executor

class Application : MultiDexApplication(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@Application))
        bind<ISchedulersContainer>() with singleton { SchedulersContainer() }
        bind<IDispatchersContainer>() with singleton { DispatchersContainer(instance()) }
        bind<IProductUseCase>() with singleton { ProductUseCase(instance()) }
        bind<Gson>() with singleton { provideGson() }
        bind<HttpClient>() with singleton { provideHttpClient() }
        bind<IProductApi>() with singleton { ProductApi(instance(), instance()) }
        bind<IProductGateway>() with singleton { ProductGateway(instance(), instance()) }
        bind<IImageLoader>() with singleton { GlideImageLoader(instance()) }
        bind<Executor>(TAG_MAIN_EXECUTOR) with singleton { provideMainExecutor() }
    }

    //<editor-fold desc="Parent methods">
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initRx()
        KodeinController.initApplicationComponent(kodein)
    }
    //</editor-fold>

    /**
     * [TimberTree] implemented for `debug` and `release` build types separately
     */
    private fun initTimber() {
        Timber.plant(TimberTree())
    }

    /**
     * [About Undeliverable Exception](https://habr.com/post/422611/)
     */
    private fun initRx() {
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Timber.e(it)
                return@setErrorHandler
            }
        }
    }

    private fun provideHttpClient() = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }

    private fun provideGson() = GsonBuilder().create()

    private fun provideMainExecutor() = object : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }

    companion object {
        const val TAG_MAIN_EXECUTOR = "mainExecutorPaging"
    }
}
