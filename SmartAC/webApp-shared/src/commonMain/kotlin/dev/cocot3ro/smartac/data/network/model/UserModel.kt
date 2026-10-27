package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@OptIn(ExperimentalJsExport::class)
@JsExport
@Serializable
data class UserModel(
    val id: Uuid,
    val name: String
)
