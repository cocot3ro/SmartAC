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

public val Icons.RawOff: ImageVector
    get() {
        if (_rawOff != null) {
            return _rawOff!!
        }
        _rawOff =
            Builder(
                    name = "RawOff",
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
                        moveTo(792.0f, 904.0f)
                        lineTo(56.0f, 168.0f)
                        lineToRelative(56.0f, -56.0f)
                        lineToRelative(736.0f, 736.0f)
                        lineToRelative(-56.0f, 56.0f)
                        close()
                        moveTo(740.0f, 600.0f)
                        lineTo(710.0f, 478.0f)
                        lineTo(686.0f, 572.0f)
                        lineTo(588.0f, 476.0f)
                        lineTo(560.0f, 360.0f)
                        horizontalLineToRelative(60.0f)
                        lineToRelative(30.0f, 120.0f)
                        lineToRelative(30.0f, -120.0f)
                        horizontalLineToRelative(60.0f)
                        lineToRelative(30.0f, 120.0f)
                        lineToRelative(30.0f, -120.0f)
                        horizontalLineToRelative(60.0f)
                        lineToRelative(-60.0f, 240.0f)
                        horizontalLineToRelative(-60.0f)
                        close()
                        moveTo(350.0f, 600.0f)
                        lineTo(392.0f, 432.0f)
                        lineTo(440.0f, 480.0f)
                        lineTo(500.0f, 540.0f)
                        horizontalLineToRelative(-74.0f)
                        lineToRelative(-16.0f, 60.0f)
                        horizontalLineToRelative(-60.0f)
                        close()
                        moveTo(120.0f, 600.0f)
                        verticalLineToRelative(-240.0f)
                        horizontalLineToRelative(140.0f)
                        quadToRelative(24.0f, 0.0f, 42.0f, 18.0f)
                        reflectiveQuadToRelative(18.0f, 42.0f)
                        verticalLineToRelative(40.0f)
                        quadToRelative(0.0f, 18.0f, -9.5f, 32.5f)
                        reflectiveQuadTo(284.0f, 516.0f)
                        lineToRelative(36.0f, 84.0f)
                        horizontalLineToRelative(-60.0f)
                        lineToRelative(-36.0f, -80.0f)
                        horizontalLineToRelative(-44.0f)
                        verticalLineToRelative(80.0f)
                        horizontalLineToRelative(-60.0f)
                        close()
                        moveTo(180.0f, 460.0f)
                        horizontalLineToRelative(80.0f)
                        verticalLineToRelative(-40.0f)
                        horizontalLineToRelative(-80.0f)
                        verticalLineToRelative(40.0f)
                        close()
                    }
                }
                .build()
        return _rawOff!!
    }

private var _rawOff: ImageVector? = null
