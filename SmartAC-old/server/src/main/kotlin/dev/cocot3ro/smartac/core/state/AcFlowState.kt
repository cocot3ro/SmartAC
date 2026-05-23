package dev.cocot3ro.smartac.core.state

sealed interface AcFlowState {
    data object Idle : AcFlowState
    data object Offline : AcFlowState
    data class Online(val acState: AcState) : AcFlowState
}
