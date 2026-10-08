package dev.cocot3ro.smartac.utils

import io.ktor.http.auth.DigestAlgorithm
import io.ktor.http.auth.toDigester

fun computeDigestHash(
    userName: String,
    realm: String,
    password: String,
    algorithm: DigestAlgorithm = DigestAlgorithm.SHA_256
): ByteArray {
    return algorithm.toDigester().digest("$userName:$realm:$password".toByteArray(Charsets.UTF_8))
}
