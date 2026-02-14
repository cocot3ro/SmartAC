package dev.cocot3ro.signalanalyzer.domain.usecases

import dev.cocot3ro.signalanalyzer.data.database.AppDatabaseRepository
import dev.cocot3ro.signalanalyzer.data.database.entity.ext.toDomain
import dev.cocot3ro.signalanalyzer.database.SignalEntity
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ManageSignalsUseCase(
    private val repository: AppDatabaseRepository
) {

    fun getAll(): Flow<List<SignalBase>> = repository.getAll().map { list ->
        list.map(SignalEntity::toDomain)
    }

    fun insert(signal: SignalBase) {
        repository.insertSignal(signal)
    }

    fun update(signal: SignalBase) {
        repository.updateSignal(signal)
    }

    fun delete(signal: SignalBase) {
        repository.deleteSignalById(signal.id)
    }

    fun updateCompareAll(compare: Boolean) {
        repository.updateCompareAll(compare)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

}
