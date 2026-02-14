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

val Icons.ModeHeat: ImageVector by lazy {
    Builder(
        name = "ModeHeat",
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
                moveTo(240.0f, 560.0f)
                quadToRelative(0.0f, 52.0f, 21.0f, 98.5f)
                reflectiveQuadToRelative(60.0f, 81.5f)
                quadToRelative(-1.0f, -5.0f, -1.0f, -9.0f)
                verticalLineToRelative(-9.0f)
                quadToRelative(0.0f, -32.0f, 12.0f, -60.0f)
                reflectiveQuadToRelative(35.0f, -51.0f)
                lineToRelative(113.0f, -111.0f)
                lineToRelative(113.0f, 111.0f)
                quadToRelative(23.0f, 23.0f, 35.0f, 51.0f)
                reflectiveQuadToRelative(12.0f, 60.0f)
                verticalLineToRelative(9.0f)
                quadToRelative(0.0f, 4.0f, -1.0f, 9.0f)
                quadToRelative(39.0f, -35.0f, 60.0f, -81.5f)
                reflectiveQuadToRelative(21.0f, -98.5f)
                quadToRelative(0.0f, -50.0f, -18.5f, -94.5f)
                reflectiveQuadTo(648.0f, 386.0f)
                quadToRelative(-20.0f, 13.0f, -42.0f, 19.5f)
                reflectiveQuadToRelative(-45.0f, 6.5f)
                quadToRelative(-62.0f, 0.0f, -107.5f, -41.0f)
                reflectiveQuadTo(401.0f, 270.0f)
                quadToRelative(-39.0f, 33.0f, -69.0f, 68.5f)
                reflectiveQuadToRelative(-50.5f, 72.0f)
                quadTo(261.0f, 447.0f, 250.5f, 485.0f)
                reflectiveQuadTo(240.0f, 560.0f)
                close()
                moveTo(480.0f, 612.0f)
                lineTo(423.0f, 668.0f)
                quadToRelative(-11.0f, 11.0f, -17.0f, 25.0f)
                reflectiveQuadToRelative(-6.0f, 29.0f)
                quadToRelative(0.0f, 32.0f, 23.5f, 55.0f)
                reflectiveQuadToRelative(56.5f, 23.0f)
                quadToRelative(33.0f, 0.0f, 56.5f, -23.0f)
                reflectiveQuadToRelative(23.5f, -55.0f)
                quadToRelative(0.0f, -16.0f, -6.0f, -29.5f)
                reflectiveQuadTo(537.0f, 668.0f)
                lineToRelative(-57.0f, -56.0f)
                close()
                moveTo(480.0f, 120.0f)
                verticalLineToRelative(132.0f)
                quadToRelative(0.0f, 34.0f, 23.5f, 57.0f)
                reflectiveQuadToRelative(57.5f, 23.0f)
                quadToRelative(18.0f, 0.0f, 33.5f, -7.5f)
                reflectiveQuadTo(622.0f, 302.0f)
                lineToRelative(18.0f, -22.0f)
                quadToRelative(74.0f, 42.0f, 117.0f, 117.0f)
                reflectiveQuadToRelative(43.0f, 163.0f)
                quadToRelative(0.0f, 134.0f, -93.0f, 227.0f)
                reflectiveQuadTo(480.0f, 880.0f)
                quadToRelative(-134.0f, 0.0f, -227.0f, -93.0f)
                reflectiveQuadToRelative(-93.0f, -227.0f)
                quadToRelative(0.0f, -129.0f, 86.5f, -245.0f)
                reflectiveQuadTo(480.0f, 120.0f)
                close()
            }
        }
        .build()
}
