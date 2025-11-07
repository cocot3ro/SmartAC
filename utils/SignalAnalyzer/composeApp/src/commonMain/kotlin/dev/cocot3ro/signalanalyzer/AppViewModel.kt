package dev.cocot3ro.signalanalyzer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.cocot3ro.signalanalyzer.domain.model.DisplaySignal
import dev.cocot3ro.signalanalyzer.domain.model.IrSignal
import dev.cocot3ro.signalanalyzer.domain.model.PulseType
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase
import dev.cocot3ro.signalanalyzer.domain.model.SignalType
import dev.cocot3ro.signalanalyzer.domain.usecases.ManageSignalsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val useCase: ManageSignalsUseCase
) : ViewModel() {

    private var diffJob: Job? = null
    private val _irDiff: SnapshotStateList<Int> = mutableStateListOf()
    private val _displayDiff: SnapshotStateList<Int> = mutableStateListOf()
    val diffList: List<Int>
        get() = when (selectedType) {
            SignalType.IR -> _irDiff
            SignalType.DISPLAY -> _displayDiff
            null -> emptyList()
        }

    var selectedType: SignalType? by mutableStateOf(null)
        private set

    private var _listCache: List<SignalBase> = emptyList()

    private val _signalList: MutableStateFlow<List<SignalBase>> = MutableStateFlow(emptyList())
    val signalList: StateFlow<List<SignalBase>> = _signalList
        .onStart {
            viewModelScope.launch(Dispatchers.IO) {
                useCase.getAll().collect { list: List<SignalBase> ->
                    _listCache = list

                    _signalList.value = when (selectedType) {
                        SignalType.IR -> list.filterIsInstance<IrSignal>()
                        SignalType.DISPLAY -> list.filterIsInstance<DisplaySignal>()
                        null -> emptyList()
                    }

                    diffJob?.cancel()
                    diffJob = launch(Dispatchers.Default) {
                        _listCache.filter { it.compare }
                            .partition { it is IrSignal }
                            .let { (ir, display) ->
                                listOf(
                                    async {
                                        val diff: List<Int> = calculateDiff(ir)
                                        _irDiff.clear()
                                        _irDiff.addAll(diff)
                                    },
                                    async {
                                        val diff: List<Int> = calculateDiff(display)
                                        _displayDiff.clear()
                                        _displayDiff.addAll(diff)
                                    }
                                ).awaitAll()
                            }
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun selectType(type: SignalType) {
        if (type == this.selectedType) return
        this.selectedType = type

        viewModelScope.launch(context = Dispatchers.IO) {
            _signalList.value = when (type) {
                SignalType.IR -> _listCache.filterIsInstance<IrSignal>()
                SignalType.DISPLAY -> _listCache.filterIsInstance<DisplaySignal>()
            }
        }
    }

    private fun calculateDiff(list: List<SignalBase>): List<Int> = buildList {
        buildMap<Int, List<PulseType>> {
            list.map(SignalBase::binaryData).forEach { bin ->
                bin.forEachIndexed { idx: Int, pulseType: PulseType ->
                    this[idx] = this[idx].orEmpty() + pulseType
                }
            }
        }
            .mapValues { (_, v: List<PulseType>) -> v.toSet() }
            .filterValues { set -> set.size > 1 }
            .keys.forEachIndexed(::add)
    }

    fun createSignal(
        signalType: SignalType,
        name: String,
        rawData: String,
        compare: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (signalType) {
                SignalType.IR -> IrSignal.from(
                    name = name,
                    rawData = rawData,
                    compare = compare
                )

                SignalType.DISPLAY -> DisplaySignal.from(
                    name = name,
                    rawData = rawData,
                    compare = compare
                )
            }.let(useCase::insert)
        }
    }

    fun updateCompare(signal: SignalBase) {
        updateSignal(
            when (signal) {
                is IrSignal -> signal.copy(compare = !signal.compare)
                is DisplaySignal -> signal.copy(compare = !signal.compare)
            }
        )
    }

    fun updateAllCompare(compare: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateCompareAll(compare)
        }
    }

    private fun updateSignal(signal: SignalBase) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.update(signal)
        }
    }

    fun updateSignal(
        id: Long,
        name: String,
        rawData: String,
        compare: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val original: SignalBase? = _listCache.find { it.id == id }

            if (original == null) {
                return@launch
            }

            val updated: SignalBase = when (original) {
                is IrSignal -> IrSignal.from(
                    name = name,
                    rawData = rawData,
                    compare = compare
                ).copy(id = id)

                is DisplaySignal -> DisplaySignal.from(
                    name = name,
                    rawData = rawData,
                    compare = compare
                ).copy(id = id)
            }

            useCase.update(updated)
        }
    }

    fun deleteSignal(signal: SignalBase) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.delete(signal)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteAll()
        }
    }
}
