package dev.cocot3ro.smartac.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.CURRENT_TEMP
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.ERROR_CODE
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.FAN_SPEED
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.IS_FAN_AUTO
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.MODE
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.POWER
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.STATUS
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.TARGET_TEMP
import dev.cocot3ro.smartac.domain.model.Status
import dev.cocot3ro.smartac.network.http.model.AcStateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class DatastoreRepository(
    @Provided private val datastore: DataStore<Preferences>
) {

    fun getLocalState(): Flow<Map<Preferences.Key<*>, Any>> =
        datastore.data.map(transform = Preferences::asMap)

    suspend fun saveStatus(status: Status) {
        datastore.edit { prefs: MutablePreferences ->
            prefs[STATUS] = status.name
        }
    }

    suspend fun saveState(stateModel: AcStateModel, status: Status? = null) {
        datastore.edit { prefs: MutablePreferences ->

            status?.let { status: Status ->
                prefs[STATUS] = status.name
            }

            stateModel.run {
                prefs[POWER] = power
                prefs[IS_FAN_AUTO] = isFanAuto
                prefs[FAN_SPEED] = fanSpeed.name
                prefs[MODE] = mode.name
                prefs[CURRENT_TEMP] = currentTemp
                prefs[TARGET_TEMP] = targetTemp
                errorCode?.let { prefs[ERROR_CODE] = it.toInt() }
                    ?: run { prefs.remove(key = ERROR_CODE) }
            }
        }
    }

    suspend fun clear() {
        datastore.edit { prefs: MutablePreferences ->
            prefs.clear()
        }
    }
}