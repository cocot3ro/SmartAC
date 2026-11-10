package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import dev.cocot3ro.smartac.CircleButtonVariant
import dev.cocot3ro.smartac.UncoloredButtonVariant
import org.jetbrains.compose.web.css.em

@Suppress("ModifierRequired")
@Composable
fun IconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.setVariable(
            ButtonVars.FontSize,
            1.em
        ), // Make button icon size relative to parent container font size
        variant = CircleButtonVariant.then(UncoloredButtonVariant)
    ) {
        content()
    }
}
