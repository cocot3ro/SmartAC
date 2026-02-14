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
import androidx.compose.ui.util.fastJoinToString
import kotlin.math.abs
import kotlin.math.roundToInt

private const val PULSE_LENGTH = 830

data class DisplaySignal(
    override val id: Long,
    override val name: String,
    override val signalData: List<Pulse>,
    override val binaryData: List<PulseType>,
    override val compare: Boolean
) : SignalBase() {

    constructor(
        id: Long, name: String, signalData: Collection<Int>, compare: Boolean
    ) : this(
        id = id, name = name, signalData = decodePulses(signalData.toList()), compare = compare
    )

    private constructor(
        id: Long, name: String, signalData: List<Pulse>, compare: Boolean
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

                var hoverKey: Int? by remember { mutableStateOf(null) }

                val (signalKeys: Map<Int, Int>, binaryKeys: Map<Int, Int>) = remember {
                    val signalMap: MutableMap<Int, Int> = mutableMapOf()
                    val binaryMap: MutableMap<Int, Int> = mutableMapOf()

                    var i = 0

                    signalData.forEachIndexed { idx: Int, (_: PulseLevel, duration: Int): Pulse ->
                        signalMap[idx] = idx

                        repeat(duration / PULSE_LENGTH) {
                            binaryMap[i++] = idx
                        }
                    }

                    signalMap to binaryMap
                }

                if (displayRaw) {
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        signalData.forEachIndexed { idx: Int, (level: PulseLevel, duration: Int): Pulse ->
                            val sign: String = when (level) {
                                PulseLevel.LOW -> "-"
                                PulseLevel.HIGH -> "+"
                            }

                            val textModifier: Modifier = Modifier.then(
                                if (signalKeys[idx] != hoverKey) Modifier else {
                                    Modifier.background(Color.Gray)
                                }
                            ).then(Modifier.pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event: PointerEvent = awaitPointerEvent()
                                        when (event.type) {
                                            PointerEventType.Enter -> hoverKey = signalKeys[idx]
                                            PointerEventType.Exit -> hoverKey = null
                                        }
                                    }
                                }
                            })

                            Text(
                                modifier = textModifier, text = "$sign$duration"
                            )
                        }
                    }
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
                ) {
                    val n = 10

                    binaryData.chunked(n).forEachIndexed { byteIndex: Int, byte: List<PulseType> ->
                        Row {
                            byte.forEachIndexed { bitIdx: Int, bit: PulseType ->

                                val idx: Int = byteIndex * n + bitIdx

                                val textModifier: Modifier = Modifier.then(
                                    if (binaryKeys[idx] != hoverKey) Modifier else {
                                        Modifier.background(Color.Gray)
                                    }
                                ).then(if (!displayRaw) Modifier else Modifier.pointerInput(Unit) {
                                    awaitPointerEventScope {
                                        while (true) {
                                            val event: PointerEvent = awaitPointerEvent()
                                            when (event.type) {
                                                PointerEventType.Enter -> hoverKey = binaryKeys[idx]
                                                PointerEventType.Exit -> hoverKey = null
                                            }
                                        }
                                    }
                                })

                                Text(
                                    modifier = textModifier, text = when (bit) {
                                        PulseType.BIT_0 -> "0"
                                        PulseType.BIT_1 -> "1"

                                        PulseType.HEADER, PulseType.PAUSE, PulseType.END -> error("Unexpected PulseType: $bit")
                                    }, color = when {
                                        compare && idx in diffList -> Color.Red
                                        else -> Color.Unspecified
                                    }
                                )
                            }
                        }
                    }
                }

                binaryData.chunked(10)
                    .map { it.drop(1).take(8) }
                    .map { byte: List<PulseType> ->
                        byte.fastJoinToString(separator = "") { bit: PulseType ->
                            when (bit) {
                                PulseType.BIT_0 -> "0"
                                PulseType.BIT_1 -> "1"

                                PulseType.HEADER,
                                PulseType.PAUSE,
                                PulseType.END -> error("Unexpected PulseType: $bit")
                            }
                        }.padEnd(8, '1')
                    }
                    .also {
                        println("Signal $id: $name")
                        println(it.joinToString(" "))
                    }
                    .map { byte: String -> Integer.parseInt(byte.reversed(), 2) }
                    .let { bytes: List<Int> ->
                        val n = 24

                        println("data -> " + bytes.take(n).joinToString(" ") { byte: Int ->
                            byte.toHex()
                        })

                        val checksum = bytes.drop(n).joinToString(" ") { byte: Int ->
                            byte.toHex()
                        }
                        println("checksum -> $checksum")


                        val sum = bytes.take(n).reduce { acc, i -> acc + i }
                        val computedChecksum: String = (0xFF - (sum and 0xFF)).toHex()

                        println("computed checksum -> $computedChecksum")

                        require(computedChecksum == checksum) {
                            buildString {
                                append("Checksum mismatch on signal $id $name -> ")
                                append("computed=$computedChecksum, ")
                                append("expected=$checksum")
                            }
                        }

                        println()
                    }
            }
        }
    }

    private fun Int.toHex() = this.toString(16).uppercase().padStart(2, '0')

    companion object {

        fun from(
            name: String, rawData: String, compare: Boolean
        ): DisplaySignal {
            val normalized: List<Pulse> = normalize(rawData)
            val binary: List<PulseType> = decodeBinary(normalized)
            return DisplaySignal(
                id = 0L,
                name = name,
                signalData = normalized,
                binaryData = binary,
                compare = compare
            )
        }

        private fun normalize(rawData: String): List<Pulse> =
            rawData.split(",").dropWhile { it.toInt() > 100_000 }.map(String::toInt)
                .map { (it.toDouble() / PULSE_LENGTH).roundToInt() * PULSE_LENGTH }
                .map { duration: Int ->
                    Pulse(
                        pulseLevel = PulseLevel.entries[duration.fastCoerceIn(0, 1)],
                        duration = abs(duration)
                    )
                }

        private fun decodePulses(signalData: List<Int>): List<Pulse> = signalData.map { duration ->
            Pulse(
                pulseLevel = PulseLevel.entries[duration.fastCoerceIn(0, 1)],
                duration = abs(duration)
            )
        }

        private fun decodeBinary(signalData: List<Pulse>): List<PulseType> = buildList {
            signalData.map { pulse ->
                repeat(pulse.duration / PULSE_LENGTH) {
                    add(PulseType.entries[pulse.pulseLevel.ordinal + 1])
                }
            }
        }
    }
}
