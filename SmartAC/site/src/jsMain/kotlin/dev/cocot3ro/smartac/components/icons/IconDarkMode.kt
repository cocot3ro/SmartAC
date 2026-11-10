package dev.cocot3ro.smartac.components.icons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.SVGSvgAttrsScope
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.px

@Suppress("ModifierRequired")
@Composable
fun IconDarkMode(
    attrs: (SVGSvgAttrsScope.() -> Unit)? = null
) {
    Svg(attrs = Modifier.size(24.px).toAttrs {

        classes("dark")

        viewBox(x = 0, y = 0, width = 24, height = 24)
        fill(Color.rgb(0xE3E3E3))

        attrs?.invoke(this)
    }) {
        Path {
            d {
                moveTo(12f, 21f)
                quadraticBezierCurve(-3.75f, 0f, -6.38f, -2.63f, true)
                smoothQuadraticBezierCurve(3f, 12f, false)
                smoothQuadraticBezierCurve(5.63f, 5.63f, false)
                smoothQuadraticBezierCurve(12f, 3f, false)
                quadraticBezierCurve(0.35f, 0f, 0.69f, 0.02f, true)
                quadraticBezierCurve(0.34f, 0.02f, 0.66f, 0.07f, true)
                quadraticBezierCurve(-1.03f, 0.73f, -1.64f, 1.89f, true)
                smoothQuadraticBezierCurve(11.1f, 7.5f, false)
                quadraticBezierCurve(0f, 2.25f, 1.57f, 3.82f, true)
                smoothQuadraticBezierCurve(16.5f, 12.9f, false)
                quadraticBezierCurve(1.38f, 0f, 2.53f, -0.61f, true)
                smoothQuadraticBezierCurve(1.88f, -1.64f, true)
                quadraticBezierCurve(0.05f, 0.33f, 0.07f, 0.66f, true)
                smoothQuadraticBezierCurve(21f, 12f, false)
                quadraticBezierCurve(0f, 3.75f, -2.63f, 6.38f, true)
                smoothQuadraticBezierCurve(12f, 21f, false)
                closePath()
                moveTo(12f, 19f)
                quadraticBezierCurve(2.2f, 0f, 3.95f, -1.21f, true)
                smoothQuadraticBezierCurve(2.55f, -3.16f, true)
                quadraticBezierCurve(-0.5f, 0.13f, -1f, 0.2f, true)
                smoothQuadraticBezierCurve(-1f, 0.08f, true)
                quadraticBezierCurve(-3.07f, 0f, -5.24f, -2.16f, true)
                quadraticBezierCurve(9.1f, 10.58f, 9.1f, 7.5f, false)
                quadraticBezierCurve(0f, -0.5f, 0.08f, -1f, true)
                smoothQuadraticBezierCurve(0.2f, -1f, true)
                quadraticBezierCurve(7.43f, 6.3f, 6.21f, 8.05f, false)
                smoothQuadraticBezierCurve(5f, 12f, false)
                quadraticBezierCurve(0f, 2.9f, 2.05f, 4.95f, true)
                smoothQuadraticBezierCurve(12f, 19f, false)
                closePath()
                moveTo(11.75f, 12.25f)
                closePath()
            }
        }
    }
}
