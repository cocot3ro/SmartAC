package dev.cocot3ro.smartac.ui

sealed class AuthState {
    data object Idle : AuthState()
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
}