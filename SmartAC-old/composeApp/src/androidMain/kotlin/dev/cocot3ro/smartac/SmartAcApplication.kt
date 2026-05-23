package dev.cocot3ro.smartac

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dev.cocot3ro.smartac.di.KoinDi
import dev.cocot3ro.smartac.di.datastoreModule
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.fileProperties
import org.koin.plugin.module.dsl.startKoin

class SmartAcApplication : Application() {

    private val observer: AppLifecycleObserver by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin<KoinDi> {
            modules(datastoreModule)

            fileProperties()

            androidLogger()
            androidContext(this@SmartAcApplication)
            analytics()
        }

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(observer)
    }
}
