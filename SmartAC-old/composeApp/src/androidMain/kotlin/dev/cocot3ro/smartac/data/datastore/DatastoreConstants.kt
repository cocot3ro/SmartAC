package dev.cocot3ro.smartac.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DatastoreConstants {
    internal const val DATASTORE_NAME: String = "smart_ac_datastore.preferences_pb"

    object Keys {
        val STATUS: Preferences.Key<String> = stringPreferencesKey(name = "status")

        val POWER: Preferences.Key<Boolean> = booleanPreferencesKey(name = "power")
        val IS_FAN_AUTO: Preferences.Key<Boolean> = booleanPreferencesKey(name = "isFanAuto")
        val FAN_SPEED: Preferences.Key<String> = stringPreferencesKey(name = "fanSpeed")
        val MODE: Preferences.Key<String> = stringPreferencesKey(name = "mode")
        val CURRENT_TEMP: Preferences.Key<Int> = intPreferencesKey(name = "currentTemp")
        val TARGET_TEMP: Preferences.Key<Int> = intPreferencesKey(name = "targetTemp")
        val ERROR_CODE: Preferences.Key<Int> = intPreferencesKey(name = "errorCode")
    }
}
