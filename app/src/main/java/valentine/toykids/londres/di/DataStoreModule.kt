package valentine.toykids.londres.di

import valentine.toykids.londres.data.datastore.GURKMOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { GURKMOnboardingPrefs(androidContext()) }
}