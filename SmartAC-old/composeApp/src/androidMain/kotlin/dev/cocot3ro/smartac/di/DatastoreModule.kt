package dev.cocot3ro.smartac.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.cocot3ro.smartac.data.datastore.DatastoreRepository
import dev.cocot3ro.smartac.data.datastore.provideDatastore
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val datastoreModule: Module = module {
    single<DataStore<Preferences>> {
        provideDatastore()
    }

    single<DatastoreRepository>()
}