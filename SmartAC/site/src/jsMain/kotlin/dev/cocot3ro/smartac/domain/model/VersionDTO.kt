package dev.cocot3ro.smartac.domain.model

import dev.cocot3ro.smartac.data.network.model.VersionModel

data class VersionDTO(
    val major: Int,
    val minor: Int,
    val patch: Int
) {
    override fun toString(): String = "$major.$minor.$patch"
}

fun VersionModel.toDto(): VersionDTO = VersionDTO(
    major = this.major,
    minor = this.minor,
    patch = this.patch,
)