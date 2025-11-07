package dev.cocot3ro.signalanalyzer.data.database

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json

object SignalDataAdapter : ColumnAdapter<List<Int>, String> {
    override fun decode(databaseValue: String): List<Int> {
        return Json.decodeFromString<List<Int>>(databaseValue)
    }

    override fun encode(value: List<Int>): String {
        return Json.encodeToString(value)
    }
}