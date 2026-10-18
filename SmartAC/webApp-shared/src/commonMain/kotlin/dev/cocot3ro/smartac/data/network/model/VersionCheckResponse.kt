package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class VersionCheckResponse(
    val current: VersionModel,

    val latest: VersionModel,

    @SerialName("update_available")
    val updateAvailable: Boolean
)