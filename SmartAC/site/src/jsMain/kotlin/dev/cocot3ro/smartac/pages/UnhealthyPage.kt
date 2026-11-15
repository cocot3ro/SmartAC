package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.sections.Page
import dev.cocot3ro.smartac.components.sections.PageCenterVariant
import dev.cocot3ro.smartac.components.sections.PageVariant
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Suppress("ModifierRequired")
@Composable
fun UnhealthyPage() {
    Page(PageVariant, PageCenterVariant) {
        Div(attrs = {
            classes("container", "container-tight", "py-4")
        }) {
            Div(attrs = {
                classes("empty")
            }) {
                H2(attrs = {
                    classes("empty-title", "mt-6")
                }) {
                    Text(value = "The API is not healthy")
                }

                SpanText(
                    modifier = Modifier.classNames("empty-subtitle", "text-secondary"),
                    text = "We'll keep checking and hope to be back soon!"
                )
            }
        }
    }
}
