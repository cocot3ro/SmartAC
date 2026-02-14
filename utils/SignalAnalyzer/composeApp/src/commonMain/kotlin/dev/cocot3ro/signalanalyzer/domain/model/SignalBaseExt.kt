package dev.cocot3ro.signalanalyzer.domain.model

import dev.cocot3ro.signalanalyzer.data.database.entity.SignalTypeEntity
import dev.cocot3ro.signalanalyzer.database.SignalEntity

fun SignalBase.toEntity(): SignalEntity = SignalEntity(
    id = this.id,
    name = this.name,
    signalData = this.signalData.map { it.duration * (-1 + it.pulseLevel.ordinal * 2) },
    type = when (this) {
        is IrSignal -> SignalTypeEntity.IR
        is DisplaySignal -> SignalTypeEntity.DISPLAY
    },
    compare = this.compare
)