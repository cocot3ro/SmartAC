package dev.cocot3ro.smartac.domain.state

import dev.cocot3ro.smartac.domain.model.AcState

sealed interface DomainFlowState {
    data object Idle : DomainFlowState
    data object Loading : DomainFlowState
    data object Connecting : DomainFlowState
    data object DeviceOffline : DomainFlowState
    data object Disconnected : DomainFlowState
    data class Success(val acState: AcState) : DomainFlowState
    data object ConnectionError : DomainFlowState
}
