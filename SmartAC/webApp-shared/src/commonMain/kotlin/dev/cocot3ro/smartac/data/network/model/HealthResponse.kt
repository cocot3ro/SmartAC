package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
@Serializable
data class HealthResponse(
    val version: VersionModel,
    val setup: Boolean
)