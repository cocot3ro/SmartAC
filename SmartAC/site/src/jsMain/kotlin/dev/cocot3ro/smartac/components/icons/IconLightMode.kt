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
fun IconLightMode(
    attrs: (SVGSvgAttrsScope.() -> Unit)? = null
) {
    Svg(attrs = Modifier.size(24.px).toAttrs {
        classes("light")

        viewBox(x = 0, y = 0, width = 24, height = 24)
        fill(Color.rgb(0xE3E3E3))

        attrs?.invoke(this)
    }) {
        Path {
            d {
                moveTo(14.13f, 14.13f)
                quadraticBezierCurve(15f, 13.25f, 15f, 12f, false)
                smoothQuadraticBezierCurve(14.13f, 9.88f, false)
                smoothQuadraticBezierCurve(12f, 9f, false)
                smoothQuadraticBezierCurve(9.88f, 9.88f, false)
                smoothQuadraticBezierCurve(9f, 12f, false)
                smoothQuadraticBezierCurve(0.88f, 2.13f, true)
                smoothQuadraticBezierCurve(12f, 15f, false)
                smoothQuadraticBezierCurve(2.13f, -0.88f, true)
                closePath()
                moveTo(8.46f, 15.54f)
                quadraticBezierCurve(7f, 14.08f, 7f, 12f, false)
                quadraticBezierCurve(7f, 9.92f, 8.46f, 8.46f, false)
                smoothQuadraticBezierCurve(12f, 7f, false)
                smoothQuadraticBezierCurve(3.54f, 1.46f, true)
                smoothQuadraticBezierCurve(17f, 12f, false)
                smoothQuadraticBezierCurve(-1.46f, 3.54f, true)
                smoothQuadraticBezierCurve(12f, 17f, false)
                quadraticBezierCurve(-2.07f, 0f, -3.54f, -1.46f, true)
                closePath()
                moveTo(5f, 13f)
                lineTo(1f, 13f)
                lineTo(1f, 11f)
                lineTo(5f, 11f)
                verticalLineTo(2f, true)
                closePath()
                moveTo(23f, 13f)
                lineTo(19f, 13f)
                lineTo(19f, 11f)
                horizontalLineTo(4f, true)
                verticalLineTo(2f, true)
                closePath()
                moveTo(11f, 5f)
                lineTo(11f, 1f)
                horizontalLineTo(2f, true)
                lineTo(13f, 5f)
                lineTo(11f, 5f)
                closePath()
                moveTo(11f, 23f)
                lineTo(11f, 19f)
                horizontalLineTo(2f, true)
                verticalLineTo(4f, true)
                lineTo(11f, 23f)
                closePath()
                moveTo(6.4f, 7.75f)
                lineTo(3.88f, 5.32f)
                lineTo(5.3f, 3.85f)
                lineTo(2.4f, 2.5f, true)
                lineTo(6.4f, 7.75f)
                closePath()
                moveTo(18.7f, 20.15f)
                lineTo(-2.42f, -2.52f, true)
                lineTo(1.32f, -1.38f, true)
                lineTo(2.52f, 2.43f, true)
                lineTo(18.7f, 20.15f)
                closePath()
                moveTo(16.25f, 6.4f)
                lineTo(2.43f, -2.52f, true)
                lineTo(20.15f, 5.3f)
                lineTo(-2.5f, 2.4f, true)
                lineTo(-1.4f, -1.3f, true)
                closePath()
                moveTo(3.85f, 18.7f)
                lineTo(2.53f, -2.43f, true)
                lineTo(7.75f, 17.6f)
                lineTo(5.33f, 20.13f)
                lineTo(3.85f, 18.7f)
                closePath()
                moveTo(12f, 12f)
                closePath()
            }
        }
    }
}
