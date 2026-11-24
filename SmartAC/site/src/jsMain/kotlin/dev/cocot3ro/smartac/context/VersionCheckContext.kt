package dev.cocot3ro.smartac.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.cocot3ro.smartac.domain.repository.VersionCheckRepository
import io.github.z4kn4fein.semver.Version
import org.koin.compose.koinInject

data class VersionCheckContext(
    val versionData: VersionData
)

data class VersionData(
    val current: Version,
    val latest: Version,
    val updateAvailable: Boolean
)

val LocalVersionCheckContext: ProvidableCompositionLocal<VersionCheckContext> = compositionLocalOf {
    error("Use inside a VersionCheckContextProvider")
}

@Composable
fun VersionCheckContextProvider(
    versionCheckRepository: VersionCheckRepository = koinInject(),
    content: @Composable () -> Unit
) {

}
