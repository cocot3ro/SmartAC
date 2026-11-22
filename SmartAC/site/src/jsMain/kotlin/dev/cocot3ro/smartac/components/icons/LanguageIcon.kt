package dev.cocot3ro.smartac.components.icons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.SVGSvgAttrsScope
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.graphics.Color
import org.jetbrains.compose.web.css.px

@Suppress("ModifierRequired")
@Composable
fun LanguageIcon(
    attrs: (SVGSvgAttrsScope.() -> Unit)? = null
) {
    Svg(attrs = {
        width(24.px)
        height(24.px)

        viewBox(x = 0, y = 0, width = 24, height = 24)
        fill(Color.rgb(0xE3E3E3))

        attrs?.invoke(this)
    }) {
        Path {
            d("M8.13,21.21Q6.3,20.43 4.94,19.06Q3.58,17.7 2.79,15.88T2,11.99T2.79,8.11T4.94,4.94T8.13,2.79T12.01,2t3.88,0.79t3.17,2.15t2.15,3.17T22,11.99t-0.79,3.89t-2.15,3.19t-3.17,2.15T12.01,22T8.13,21.21ZM12,19.95q0.65-0.9 1.13-1.88T13.9,16H10.1q0.3,1.1 0.77,2.07T12,19.95Zm-2.6-0.4q-0.45-0.82-0.79-1.71T8.05,16H5.1q0.72,1.25 1.81,2.18T9.4,19.55Zm5.2,0q1.4-0.45 2.49-1.38T18.9,16H15.95q-0.22,0.95-0.56,1.84q-0.34,0.89-0.79,1.71ZM4.25,14h3.4q-0.08-0.5-0.11-0.99T7.5,12t0.04-1.01T7.65,10H4.25q-0.13,0.5-0.19,0.99Q4,11.48 4,12q0,0.52 0.06,1.01T4.25,14Zm5.4,0h4.7q0.07-0.5 0.11-0.99T14.5,12t-0.04-1.01T14.35,10H9.65q-0.07,0.5-0.11,0.99Q9.5,11.48 9.5,12q0,0.52 0.04,1.01Q9.58,13.5 9.65,14Zm6.7,0h3.4q0.13-0.5 0.19-0.99T20,12t-0.06-1.01T19.75,10h-3.4q0.07,0.5 0.11,0.99Q16.5,11.48 16.5,12q0,0.52-0.04,1.01T16.35,14Zm-0.4-6H18.9q-0.73-1.25-1.81-2.18T14.6,4.45q0.45,0.82 0.79,1.71T15.95,8ZM10.1,8h3.8q-0.3-1.1-0.78-2.07T12,4.05q-0.65,0.9-1.13,1.88T10.1,8Zm-5,0H8.05Q8.28,7.05 8.61,6.16T9.4,4.45Q8,4.9 6.91,5.82T5.1,8Z")
        }
    }
}
