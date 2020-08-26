package ng.mathemandy.venten

import android.app.Application
import ng.mathemandy.venten.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp  : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApp)
            modules(appModule)
        }
    }
}