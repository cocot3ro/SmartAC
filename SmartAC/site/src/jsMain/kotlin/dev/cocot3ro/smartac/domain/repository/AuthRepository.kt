package dev.cocot3ro.smartac.domain.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.annotation.Single

@Single
class AuthRepository {

    var authenticated: Boolean? by mutableStateOf(null)
        private set

    fun login(password: String) {

    }

    fun logout() {

    }
}
