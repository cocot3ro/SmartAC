package dev.cocot3ro.smartac.core.auth

import de.mkammerer.argon2.Argon2Factory
import org.koin.core.annotation.Factory

@Factory
class PasswordHasher {

    private val argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id
    )

    fun hash(password: String): String {
        val chars = password.toCharArray()

        return try {
            // 2 iteraciones, 19 MiB de memoria, paralelismo 1
            argon2.hash(2, 19 * 1024, 1, chars)
        } finally {
            argon2.wipeArray(chars)
        }
    }

    fun verify(
        encodedHash: String,
        password: String
    ): Boolean {
        val chars = password.toCharArray()

        return try {
            argon2.verify(encodedHash, chars)
        } finally {
            argon2.wipeArray(chars)
        }
    }
}
