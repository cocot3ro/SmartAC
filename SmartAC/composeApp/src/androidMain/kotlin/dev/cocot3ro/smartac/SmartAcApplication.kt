package dev.cocot3ro.smartac

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dev.cocot3ro.smartac.di.KoinDi
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.ksp.generated.startKoin

class SmartAcApplication : Application() {

    private val observer: AppLifecycleObserver by inject()

    override fun onCreate() {
        super.onCreate()

        KoinDi.startKoin {
            androidLogger()
            androidContext(this@SmartAcApplication)
            analytics()
        }

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(observer)
    }
}
