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

public val Icons.RawOn: ImageVector
    get() {
        if (_rawOn != null) {
            return _rawOn!!
        }
        _rawOn =
            Builder(
                    name = "RawOn",
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
                        moveTo(350.0f, 600.0f)
                        lineTo(410.0f, 360.0f)
                        horizontalLineToRelative(100.0f)
                        lineToRelative(60.0f, 240.0f)
                        horizontalLineToRelative(-60.0f)
                        lineToRelative(-14.0f, -60.0f)
                        horizontalLineToRelative(-70.0f)
                        lineToRelative(-16.0f, 60.0f)
                        horizontalLineToRelative(-60.0f)
                        close()
                        moveTo(620.0f, 600.0f)
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
                        lineToRelative(-30.0f, -122.0f)
                        lineToRelative(-30.0f, 122.0f)
                        horizontalLineToRelative(-60.0f)
                        close()
                        moveTo(440.0f, 480.0f)
                        horizontalLineToRelative(40.0f)
                        lineToRelative(-10.0f, -40.0f)
                        horizontalLineToRelative(-20.0f)
                        lineToRelative(-10.0f, 40.0f)
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
        return _rawOn!!
    }

private var _rawOn: ImageVector? = null
