package dev.cocot3ro.signalanalyzer.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class SignalBase {

    abstract val id: Long
    abstract val name: String
    abstract val signalData: List<Pulse>
    abstract val binaryData: List<PulseType>
    abstract val compare: Boolean

    @Composable
    abstract fun Render(modifier: Modifier, diffList: List<Int>, displayRaw: Boolean)
}