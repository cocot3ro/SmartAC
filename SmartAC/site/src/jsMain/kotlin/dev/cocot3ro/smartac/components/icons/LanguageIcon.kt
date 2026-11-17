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
fun LanguageIcon(
    attrs: (SVGSvgAttrsScope.() -> Unit)? = null
) {
    Svg(attrs = Modifier.size(24.px).toAttrs {
        viewBox(x = 0, y = 0, width = 24, height = 24)
        fill(Color.rgb(0xE3E3E3))

        attrs?.invoke(this)
    }) {
        Path {
            d {
                moveTo(8.13f, 21.21f)
                quadraticBezierCurve(6.3f, 20.43f, 4.94f, 19.06f, false)
                quadraticBezierCurve(3.58f, 17.7f, 2.79f, 15.88f, false)
                smoothQuadraticBezierCurve(2f, 11.99f, false)
                smoothQuadraticBezierCurve(2.79f, 8.11f, false)
                smoothQuadraticBezierCurve(4.94f, 4.94f, false)
                smoothQuadraticBezierCurve(8.13f, 2.79f, false)
                smoothQuadraticBezierCurve(12.01f, 2f, false)
                smoothQuadraticBezierCurve(3.88f, 0.79f, true)
                smoothQuadraticBezierCurve(3.17f, 2.15f, true)
                smoothQuadraticBezierCurve(2.15f, 3.17f, true)
                smoothQuadraticBezierCurve(22f, 11.99f, false)
                smoothQuadraticBezierCurve(-0.79f, 3.89f, true)
                smoothQuadraticBezierCurve(-2.15f, 3.19f, true)
                smoothQuadraticBezierCurve(-3.17f, 2.15f, true)
                smoothQuadraticBezierCurve(12.01f, 22f, false)
                smoothQuadraticBezierCurve(8.13f, 21.21f, false)
                closePath()
                moveTo(12f, 19.95f)
                quadraticBezierCurve(0.65f, -0.9f, 1.13f, -1.88f, true)
                smoothQuadraticBezierCurve(13.9f, 16f, false)
                lineTo(10.1f, 16f)
                quadraticBezierCurve(0.3f, 1.1f, 0.77f, 2.07f, true)
                smoothQuadraticBezierCurve(12f, 19.95f, false)
                closePath()
                moveTo(9.4f, 19.55f)
                quadraticBezierCurve(-0.45f, -0.82f, -0.79f, -1.71f, true)
                smoothQuadraticBezierCurve(8.05f, 16f, false)
                lineTo(5.1f, 16f)
                quadraticBezierCurve(0.72f, 1.25f, 1.81f, 2.18f, true)
                smoothQuadraticBezierCurve(9.4f, 19.55f, false)
                closePath()
                moveTo(14.6f, 19.55f)
                quadraticBezierCurve(1.4f, -0.45f, 2.49f, -1.38f, true)
                smoothQuadraticBezierCurve(18.9f, 16f, false)
                lineTo(15.95f, 16f)
                quadraticBezierCurve(-0.22f, 0.95f, -0.56f, 1.84f, true)
                quadraticBezierCurve(-0.34f, 0.89f, -0.79f, 1.71f, true)
                closePath()
                moveTo(4.25f, 14f)
                horizontalLineTo(3.4f, true)
                quadraticBezierCurve(-0.08f, -0.5f, -0.11f, -0.99f, true)
                smoothQuadraticBezierCurve(7.5f, 12f, false)
                smoothQuadraticBezierCurve(0.04f, -1.01f, true)
                smoothQuadraticBezierCurve(7.65f, 10f, false)
                lineTo(4.25f, 10f)
                quadraticBezierCurve(-0.13f, 0.5f, -0.19f, 0.99f, true)
                quadraticBezierCurve(4f, 11.48f, 4f, 12f, false)
                quadraticBezierCurve(0f, 0.52f, 0.06f, 1.01f, true)
                smoothQuadraticBezierCurve(4.25f, 14f, false)
                closePath()
                moveTo(9.65f, 14f)
                horizontalLineTo(4.7f, true)
                quadraticBezierCurve(0.07f, -0.5f, 0.11f, -0.99f, true)
                smoothQuadraticBezierCurve(14.5f, 12f, false)
                smoothQuadraticBezierCurve(-0.04f, -1.01f, true)
                smoothQuadraticBezierCurve(14.35f, 10f, false)
                lineTo(9.65f, 10f)
                quadraticBezierCurve(-0.07f, 0.5f, -0.11f, 0.99f, true)
                quadraticBezierCurve(9.5f, 11.48f, 9.5f, 12f, false)
                quadraticBezierCurve(0f, 0.52f, 0.04f, 1.01f, true)
                quadraticBezierCurve(9.58f, 13.5f, 9.65f, 14f, false)
                closePath()
                moveTo(16.35f, 14f)
                horizontalLineTo(3.4f, true)
                quadraticBezierCurve(0.13f, -0.5f, 0.19f, -0.99f, true)
                smoothQuadraticBezierCurve(20f, 12f, false)
                smoothQuadraticBezierCurve(-0.06f, -1.01f, true)
                smoothQuadraticBezierCurve(19.75f, 10f, false)
                horizontalLineTo(-3.4f, true)
                quadraticBezierCurve(0.07f, 0.5f, 0.11f, 0.99f, true)
                quadraticBezierCurve(16.5f, 11.48f, 16.5f, 12f, false)
                quadraticBezierCurve(0f, 0.52f, -0.04f, 1.01f, true)
                smoothQuadraticBezierCurve(16.35f, 14f, false)
                closePath()
                moveTo(15.95f, 8f)
                lineTo(18.9f, 8f)
                quadraticBezierCurve(-0.73f, -1.25f, -1.81f, -2.18f, true)
                smoothQuadraticBezierCurve(14.6f, 4.45f, false)
                quadraticBezierCurve(0.45f, 0.82f, 0.79f, 1.71f, true)
                smoothQuadraticBezierCurve(15.95f, 8f, false)
                closePath()
                moveTo(10.1f, 8f)
                horizontalLineTo(3.8f, true)
                quadraticBezierCurve(-0.3f, -1.1f, -0.78f, -2.07f, true)
                smoothQuadraticBezierCurve(12f, 4.05f, false)
                quadraticBezierCurve(-0.65f, 0.9f, -1.13f, 1.88f, true)
                smoothQuadraticBezierCurve(10.1f, 8f, false)
                closePath()
                moveTo(5.1f, 8f)
                lineTo(8.05f, 8f)
                quadraticBezierCurve(8.28f, 7.05f, 8.61f, 6.16f, false)
                smoothQuadraticBezierCurve(9.4f, 4.45f, false)
                quadraticBezierCurve(8f, 4.9f, 6.91f, 5.82f, false)
                smoothQuadraticBezierCurve(5.1f, 8f, false)
                closePath()
            }
        }
    }
}
