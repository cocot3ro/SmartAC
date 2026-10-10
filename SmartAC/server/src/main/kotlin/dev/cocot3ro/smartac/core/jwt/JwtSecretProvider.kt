package dev.cocot3ro.smartac.core.jwt

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Property
import org.koin.core.annotation.Provided
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.PosixFilePermissions
import java.security.SecureRandom
import kotlin.io.encoding.Base64

@Factory
class JwtSecretProvider(
    @Provided @Property("data_dir") private val dataDir: String
) {

    fun loadOrCreate(): String {

        val configDir: Path = Path.of(dataDir).resolve("config")
        val secretFile: Path = configDir.resolve("jwt.secret")

        Files.createDirectories(configDir)

        if (Files.exists(secretFile)) {
            return readSecret(path = secretFile)
        }

        val random = SecureRandom()
        val secret: String = ByteArray(size = 32).apply(random::nextBytes)
            .let(Base64::encode)

        val tempFile = Files.createTempFile(
            configDir,
            ".jwt-secret-",
            ".tmp",
            PosixFilePermissions.asFileAttribute(
                PosixFilePermissions.fromString("rw-------")
            )
        )

        try {
            Files.writeString(
                tempFile,
                secret,
                StandardOpenOption.TRUNCATE_EXISTING
            )

            try {
                Files.move(
                    tempFile,
                    secretFile,
                    StandardCopyOption.ATOMIC_MOVE
                )
            } catch (_: FileAlreadyExistsException) {
                // Otro proceso lo creó primero.
            }

            if (Files.exists(secretFile)) {
                return readSecret(secretFile)
            }

            error("No se pudo crear el secreto JWT")
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    private fun readSecret(path: Path): String {
        val secret: String = Files.readString(path).trim()

        require(secret.isNotEmpty()) {
            "El archivo del secreto JWT está vacío: $path"
        }

        return secret
    }

}