package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class UserModel(
    val id: Uuid,
    val name: String
)
