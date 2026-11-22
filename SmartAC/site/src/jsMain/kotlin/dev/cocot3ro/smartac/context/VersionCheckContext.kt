package dev.cocot3ro.smartac.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.cocot3ro.smartac.domain.model.VersionDTO
import dev.cocot3ro.smartac.domain.repository.VersionCheckRepository
import org.koin.compose.koinInject

data class VersionCheckContext(
    val versionData: VersionData
)

data class VersionData(
    val current: VersionDTO,
    val latest: VersionDTO,
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
