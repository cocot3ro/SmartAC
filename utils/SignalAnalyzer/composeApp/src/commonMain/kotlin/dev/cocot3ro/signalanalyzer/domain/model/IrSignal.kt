package dev.cocot3ro.signalanalyzer.domain.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import kotlin.math.abs

private const val HEADER_1 = -9000
private const val HEADER_2 = 4500
private const val BIT_MARK = -615
private const val ZERO_SPACE = 530
private const val ONE_SPACE = 1640
private const val PAUSE_SPACE = 7940

data class IrSignal(
    override val id: Long,
    override val name: String,
    override val signalData: List<Pulse>,
    override val binaryData: List<PulseType>,
    override val compare: Boolean
) : SignalBase() {

    constructor(
        id: Long,
        name: String,
        signalData: Collection<Int>,
        compare: Boolean
    ) : this(
        id = id,
        name = name,
        signalData = decodePulses(signalData = signalData.toList()),
        compare = compare
    )

    private constructor(
        id: Long,
        name: String,
        signalData: List<Pulse>,
        compare: Boolean
    ) : this(
        id = id,
        name = name,
        signalData = signalData,
        binaryData = decodeBinary(signalData),
        compare = compare
    )

    @Composable
    override fun Render(modifier: Modifier, diffList: List<Int>, displayRaw: Boolean) {
        Card(modifier = modifier) {
            Column(modifier = Modifier.fillMaxSize().padding(all = 8.dp)) {
                Text(text = "$id - $name")

                var hoverIndex: Int? by remember { mutableStateOf(null) }

                if (displayRaw) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        signalData.chunked(2)
                            .forEachIndexed { idx: Int, pulses: List<Pulse> ->
                                Row {
                                    pulses.forEach { (level: PulseLevel, duration: Int): Pulse ->
                                        val sign: String = when (level) {
                                            PulseLevel.LOW -> "-"
                                            PulseLevel.HIGH -> "+"
                                        }

                                        val textModifier: Modifier = Modifier.then(
                                            if (idx != hoverIndex) Modifier else {
                                                Modifier.background(Color.Gray)
                                            }
                                        ).then(Modifier.pointerInput(Unit) {
                                            awaitPointerEventScope {
                                                while (true) {
                                                    val event: PointerEvent = awaitPointerEvent()
                                                    when (event.type) {
                                                        PointerEventType.Enter -> hoverIndex = idx
                                                        PointerEventType.Exit -> hoverIndex = null
                                                    }
                                                }
                                            }
                                        })

                                        Text(
                                            modifier = textModifier,
                                            text = "$sign$duration"
                                        )
                                    }
                                }
                            }
                    }
                }

                FlowRow(modifier = Modifier.fillMaxWidth()) {

                    var i = 0

                    binaryData.forEachIndexed { idx: Int, pulse: PulseType ->
                        val textModifier: Modifier = Modifier.then(
                            if (idx != hoverIndex) Modifier else {
                                Modifier.background(Color.Gray)
                            }
                        ).then(Modifier.pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event: PointerEvent = awaitPointerEvent()
                                    when (event.type) {
                                        PointerEventType.Enter -> hoverIndex = idx
                                        PointerEventType.Exit -> hoverIndex = null
                                    }
                                }
                            }
                        })

                        Row {
                            when (pulse) {
                                PulseType.BIT_0,
                                PulseType.BIT_1 -> i++

                                else -> Unit
                            }

                            val text: String = when (pulse) {
                                PulseType.HEADER -> "H"
                                PulseType.BIT_0 -> "0"
                                PulseType.BIT_1 -> "1"
                                PulseType.PAUSE -> "P"
                                PulseType.END -> "E"
                            }

                            Text(
                                modifier = textModifier,
                                text = text,
                                color = if (compare && idx in diffList) Color.Red else Color.Unspecified
                            )

                            if (i % 8 == 0) {
                                Text(text = " ")
                            }

                            if (pulse == PulseType.HEADER) {
                                Text(text = " ")
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun from(
            name: String,
            rawData: String,
            compare: Boolean
        ): IrSignal {
            val normalized: List<Pulse> = normalize(rawData)
            val binary: List<PulseType> = decodeBinary(signalData = normalized)
            return IrSignal(
                id = 0L,
                name = name,
                signalData = normalized,
                binaryData = binary,
                compare = compare
            )
        }

        private fun normalize(rawData: String): List<Pulse> = rawData.split(",")
            .dropWhile { it.toInt() > 100_000 }
            .map(String::toInt)
            .mapIndexed { idx, duration ->
                return@mapIndexed when {
                    idx == 0 -> HEADER_1
                    idx == 1 -> HEADER_2
                    duration < 0 -> BIT_MARK
                    duration > 6000 -> PAUSE_SPACE
                    duration > 1000 -> ONE_SPACE
                    else -> ZERO_SPACE
                }
            }
            .map { value: Int ->
                Pulse(
                    pulseLevel = PulseLevel.entries[value.fastCoerceIn(0, 1)],
                    duration = abs(value)
                )
            }

        private fun decodePulses(signalData: List<Int>): List<Pulse> = signalData.map {
            Pulse(
                pulseLevel = PulseLevel.entries[it.fastCoerceIn(0, 1)],
                duration = abs(it)
            )
        }

        private fun decodeBinary(signalData: List<Pulse>): List<PulseType> = buildList {
            signalData.drop(2) // Drop the header
                .also { add(PulseType.HEADER) }
                .chunked(2) // Group by pairs of (LOW, HIGH)
                .let { list ->
                    list.forEachIndexed { idx, l ->
                        if (idx == list.lastIndex) {
                            add(PulseType.END)
                            return@forEachIndexed
                        }

                        val (low: Pulse, high: Pulse) = l

                        if (low.duration != abs(BIT_MARK)) return@forEachIndexed

                        when (high.duration) {
                            ZERO_SPACE -> add(PulseType.BIT_0)
                            ONE_SPACE -> add(PulseType.BIT_1)
                            PAUSE_SPACE -> add(PulseType.PAUSE)
                        }
                    }
                }
        }
    }
}
