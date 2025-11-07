package dev.cocot3ro.signalanalyzer.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Icons.NestDisplay: ImageVector
    get() {
        if (_nestDisplay != null) {
            return _nestDisplay!!
        }
        _nestDisplay =
            Builder(
                name = "NestDisplay",
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 960.0f,
                viewportHeight = 960.0f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color(0xFFe3e3e3)),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = NonZero,
                    ) {
                        moveTo(480.0f, 760.0f)
                        quadToRelative(-99.0f, 0.0f, -169.5f, -13.5f)
                        reflectiveQuadTo(240.0f, 714.0f)
                        verticalLineToRelative(-34.0f)
                        horizontalLineToRelative(-73.0f)
                        quadToRelative(-35.0f, 0.0f, -59.0f, -26.0f)
                        reflectiveQuadToRelative(-21.0f, -61.0f)
                        lineToRelative(27.0f, -320.0f)
                        quadToRelative(2.0f, -31.0f, 25.0f, -52.0f)
                        reflectiveQuadToRelative(55.0f, -21.0f)
                        horizontalLineToRelative(572.0f)
                        quadToRelative(32.0f, 0.0f, 55.0f, 21.0f)
                        reflectiveQuadToRelative(25.0f, 52.0f)
                        lineToRelative(27.0f, 320.0f)
                        quadToRelative(3.0f, 35.0f, -21.0f, 61.0f)
                        reflectiveQuadToRelative(-59.0f, 26.0f)
                        horizontalLineToRelative(-73.0f)
                        verticalLineToRelative(34.0f)
                        quadToRelative(0.0f, 19.0f, -70.5f, 32.5f)
                        reflectiveQuadTo(480.0f, 760.0f)
                        close()
                        moveTo(167.0f, 600.0f)
                        horizontalLineToRelative(626.0f)
                        lineToRelative(-27.0f, -320.0f)
                        lineTo(194.0f, 280.0f)
                        lineToRelative(-27.0f, 320.0f)
                        close()
                        moveTo(480.0f, 440.0f)
                        close()
                    }
                }
                .build()
        return _nestDisplay!!
    }

private var _nestDisplay: ImageVector? = null
