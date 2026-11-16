package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import dev.cocot3ro.smartac.components.sections.Page
import dev.cocot3ro.smartac.components.sections.PageCenterVariant
import dev.cocot3ro.smartac.components.widgets.Loading
import org.jetbrains.compose.web.dom.Div

@Suppress("ModifierRequired")
@Composable
fun LoadingPage(label: String? = null, showLogo: Boolean= false) {
    Page(PageCenterVariant) {
        Div(attrs = {
            classes("container-tight", "py-4")
        }) {
            Loading(label, showLogo)
        }
    }
}
