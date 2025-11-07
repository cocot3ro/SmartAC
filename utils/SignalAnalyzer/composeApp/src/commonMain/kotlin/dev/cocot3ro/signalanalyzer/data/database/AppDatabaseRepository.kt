package dev.cocot3ro.signalanalyzer.data.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.cocot3ro.signalanalyzer.database.AppDatabase
import dev.cocot3ro.signalanalyzer.database.SignalEntity
import dev.cocot3ro.signalanalyzer.database.SignalEntityQueries
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase
import dev.cocot3ro.signalanalyzer.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class AppDatabaseRepository(driver: JdbcSqliteDriver) {

    private val database: AppDatabase = AppDatabase(
        driver = driver,
        SignalEntityAdapter = SignalEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
            signalDataAdapter = SignalDataAdapter
        )
    )

    private val signalDao: SignalEntityQueries = database.signalEntityQueries

    fun getAll(): Flow<List<SignalEntity>> = signalDao.selectAll()
        .asFlow()
        .mapToList(Dispatchers.IO)

    fun insertSignal(signal: SignalBase) {
        val entity: SignalEntity = signal.toEntity()

        signalDao.insert(
            type = entity.type,
            name = entity.name,
            signalData = entity.signalData,
            compare = entity.compare
        )
    }

    fun updateSignal(signal: SignalBase) {

        val entity: SignalEntity = signal.toEntity()

        signalDao.update(
            id = entity.id,
            name = entity.name,
            signalData = entity.signalData,
            compare = entity.compare,
        )
    }

    fun deleteSignalById(id: Long) {
        signalDao.deleteById(id)
    }

    fun updateCompareAll(compare: Boolean) {
        signalDao.transaction {
            signalDao.updateCompareAll(compare)
        }
    }

    fun deleteAll() {
        signalDao.transaction {
            signalDao.deleteAll()
        }
    }
}
