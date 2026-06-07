package valentine.toykids.londres

import android.app.Application
import valentine.toykids.londres.di.dataModule
import valentine.toykids.londres.di.dispatcherModule
import valentine.toykids.londres.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class GURKMApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@GURKMApplication)
            modules(appModules)
        }
    }
}