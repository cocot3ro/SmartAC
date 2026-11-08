package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.widgets.Card
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Main

@Suppress("ModifierRequired", "MultipleContentEmitters")
@Composable
fun SetupPage() {
    Page {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(32.cssRem)
                .padding(all = 1.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(
                attrs = Modifier
                    .display(DisplayStyle.Flex)
                    .toAttrs()
            ) {


                Row(modifier = Modifier) {
                    LocalePicker()
                    ThemeSwitcher()
                }
            }

            Main {
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier.padding(all = 1.cssRem),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        H2 {
                            SpanText("¡Bienvenido!")
                        }
                    }
                }
            }
        }
    }
}
