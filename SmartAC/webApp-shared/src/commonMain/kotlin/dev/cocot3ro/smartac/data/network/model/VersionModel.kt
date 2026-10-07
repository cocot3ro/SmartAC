package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class VersionModel(
    val major: Int,
    val minor: Int,
    val patch: Int
)
