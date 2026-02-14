package dev.cocot3ro.smartac.ui.icons

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

val Icons.ModeHeatCool: ImageVector
    get() {
        if (_modeHeatCool != null) {
            return _modeHeatCool!!
        }
        _modeHeatCool =
            Builder(
                name = "ModeHeatCool",
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
                        moveTo(277.0f, 675.0f)
                        quadToRelative(-69.0f, -26.0f, -113.0f, -86.5f)
                        reflectiveQuadTo(120.0f, 450.0f)
                        quadToRelative(0.0f, -79.0f, 37.5f, -140.5f)
                        reflectiveQuadTo(240.0f, 206.0f)
                        quadToRelative(45.0f, -42.0f, 82.5f, -64.0f)
                        lineToRelative(37.5f, -22.0f)
                        verticalLineToRelative(99.0f)
                        quadToRelative(0.0f, 25.0f, 18.0f, 42.5f)
                        reflectiveQuadToRelative(43.0f, 17.5f)
                        quadToRelative(14.0f, 0.0f, 26.0f, -6.0f)
                        reflectiveQuadToRelative(20.0f, -17.0f)
                        lineToRelative(13.0f, -16.0f)
                        quadToRelative(38.0f, 22.0f, 65.0f, 54.0f)
                        reflectiveQuadToRelative(41.0f, 72.0f)
                        lineToRelative(-67.0f, 67.0f)
                        quadToRelative(-2.0f, -24.0f, -11.5f, -47.0f)
                        reflectiveQuadTo(482.0f, 344.0f)
                        quadToRelative(-14.0f, 8.0f, -29.5f, 11.5f)
                        reflectiveQuadTo(421.0f, 359.0f)
                        quadToRelative(-44.0f, 0.0f, -79.5f, -24.5f)
                        reflectiveQuadTo(290.0f, 269.0f)
                        quadToRelative(-38.0f, 37.0f, -64.0f, 82.5f)
                        reflectiveQuadTo(200.0f, 450.0f)
                        quadToRelative(0.0f, 31.0f, 11.0f, 58.5f)
                        reflectiveQuadToRelative(30.0f, 48.5f)
                        quadToRelative(2.0f, -20.0f, 11.5f, -37.5f)
                        reflectiveQuadTo(276.0f, 488.0f)
                        lineToRelative(84.0f, -84.0f)
                        lineToRelative(86.0f, 84.0f)
                        quadToRelative(2.0f, 2.0f, 4.0f, 5.0f)
                        reflectiveQuadToRelative(4.0f, 5.0f)
                        lineToRelative(-57.0f, 57.0f)
                        quadToRelative(-2.0f, -3.0f, -3.5f, -5.0f)
                        reflectiveQuadToRelative(-3.5f, -4.0f)
                        lineToRelative(-30.0f, -29.0f)
                        lineToRelative(-28.0f, 28.0f)
                        quadToRelative(-5.0f, 5.0f, -8.5f, 11.5f)
                        reflectiveQuadTo(320.0f, 571.0f)
                        quadToRelative(0.0f, 12.0f, 7.0f, 21.5f)
                        reflectiveQuadToRelative(18.0f, 14.5f)
                        lineToRelative(-68.0f, 68.0f)
                        close()
                        moveTo(360.0f, 404.0f)
                        close()
                        moveTo(360.0f, 404.0f)
                        close()
                        moveTo(296.0f, 880.0f)
                        lineToRelative(-56.0f, -56.0f)
                        lineToRelative(544.0f, -544.0f)
                        lineToRelative(56.0f, 56.0f)
                        lineToRelative(-144.0f, 144.0f)
                        horizontalLineToRelative(144.0f)
                        verticalLineToRelative(80.0f)
                        lineTo(616.0f, 560.0f)
                        lineToRelative(-20.0f, 20.0f)
                        lineToRelative(60.0f, 60.0f)
                        horizontalLineToRelative(184.0f)
                        verticalLineToRelative(80.0f)
                        lineTo(736.0f, 720.0f)
                        lineToRelative(84.0f, 84.0f)
                        lineToRelative(-56.0f, 56.0f)
                        lineToRelative(-84.0f, -84.0f)
                        verticalLineToRelative(104.0f)
                        horizontalLineToRelative(-80.0f)
                        verticalLineToRelative(-184.0f)
                        lineToRelative(-60.0f, -60.0f)
                        lineToRelative(-20.0f, 20.0f)
                        verticalLineToRelative(224.0f)
                        horizontalLineToRelative(-80.0f)
                        verticalLineToRelative(-144.0f)
                        lineTo(296.0f, 880.0f)
                        close()
                    }
                }
                .build()
        return _modeHeatCool!!
    }

private var _modeHeatCool: ImageVector? = null
