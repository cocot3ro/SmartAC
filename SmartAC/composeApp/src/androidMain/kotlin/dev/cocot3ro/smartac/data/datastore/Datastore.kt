package dev.cocot3ro.smartac.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Single
fun provideDatastore(scope: Scope): DataStore<Preferences> = scope.run {
    return PreferenceDataStoreFactory.createWithPath {
        androidContext().filesDir
            .resolve(relative = DatastoreConstants.DATASTORE_NAME)
            .absolutePath.toPath()
    }
}
