package dev.cocot3ro.signalanalyzer.data.database.entity.ext

import dev.cocot3ro.signalanalyzer.data.database.entity.SignalTypeEntity
import dev.cocot3ro.signalanalyzer.database.SignalEntity
import dev.cocot3ro.signalanalyzer.domain.model.DisplaySignal
import dev.cocot3ro.signalanalyzer.domain.model.IrSignal
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase

fun SignalEntity.toDomain(): SignalBase {
    return when (type) {
        SignalTypeEntity.IR -> IrSignal(
            id = this.id,
            name = this.name,
            signalData = this.signalData,
            compare = compare
        )

        SignalTypeEntity.DISPLAY -> DisplaySignal(
            id = this.id,
            name = this.name,
            signalData = this.signalData,
            compare = compare
        )
    }
}