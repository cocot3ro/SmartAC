package dev.cocot3ro.smartac.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

fun Scope.provideDatastore(): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath {
    androidContext().filesDir
        .resolve(relative = DatastoreConstants.DATASTORE_NAME)
        .absolutePath.toPath()
}
