package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class VersionModel(
    val major: Int,
    val minor: Int,
    val patch: Int
)
