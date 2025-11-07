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

val Icons.SettingsRemote: ImageVector
    get() {
        if (_settingsRemote != null) {
            return _settingsRemote!!
        }
        _settingsRemote =
            Builder(
                name = "SettingsRemote",
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
                        moveTo(360.0f, 920.0f)
                        quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
                        reflectiveQuadTo(320.0f, 880.0f)
                        verticalLineToRelative(-480.0f)
                        quadToRelative(0.0f, -17.0f, 11.5f, -28.5f)
                        reflectiveQuadTo(360.0f, 360.0f)
                        horizontalLineToRelative(240.0f)
                        quadToRelative(17.0f, 0.0f, 28.5f, 11.5f)
                        reflectiveQuadTo(640.0f, 400.0f)
                        verticalLineToRelative(480.0f)
                        quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
                        reflectiveQuadTo(600.0f, 920.0f)
                        lineTo(360.0f, 920.0f)
                        close()
                        moveTo(480.0f, 570.0f)
                        quadToRelative(21.0f, 0.0f, 35.5f, -14.5f)
                        reflectiveQuadTo(530.0f, 520.0f)
                        quadToRelative(0.0f, -21.0f, -14.5f, -35.5f)
                        reflectiveQuadTo(480.0f, 470.0f)
                        quadToRelative(-21.0f, 0.0f, -35.5f, 14.5f)
                        reflectiveQuadTo(430.0f, 520.0f)
                        quadToRelative(0.0f, 21.0f, 14.5f, 35.5f)
                        reflectiveQuadTo(480.0f, 570.0f)
                        close()
                        moveTo(338.0f, 298.0f)
                        lineToRelative(-56.0f, -56.0f)
                        quadToRelative(40.0f, -40.0f, 91.0f, -61.0f)
                        reflectiveQuadToRelative(107.0f, -21.0f)
                        quadToRelative(56.0f, 0.0f, 107.0f, 21.0f)
                        reflectiveQuadToRelative(91.0f, 61.0f)
                        lineToRelative(-56.0f, 56.0f)
                        quadToRelative(-29.0f, -29.0f, -65.5f, -43.5f)
                        reflectiveQuadTo(480.0f, 240.0f)
                        quadToRelative(-40.0f, 0.0f, -76.5f, 14.5f)
                        reflectiveQuadTo(338.0f, 298.0f)
                        close()
                        moveTo(226.0f, 186.0f)
                        lineToRelative(-58.0f, -58.0f)
                        quadToRelative(63.0f, -61.0f, 143.5f, -94.5f)
                        reflectiveQuadTo(480.0f, 0.0f)
                        quadToRelative(88.0f, 0.0f, 168.5f, 33.5f)
                        reflectiveQuadTo(790.0f, 130.0f)
                        lineToRelative(-56.0f, 56.0f)
                        quadToRelative(-50.0f, -52.0f, -116.0f, -79.0f)
                        reflectiveQuadToRelative(-138.0f, -27.0f)
                        quadToRelative(-72.0f, 0.0f, -138.0f, 27.0f)
                        reflectiveQuadToRelative(-116.0f, 79.0f)
                        close()
                        moveTo(400.0f, 840.0f)
                        horizontalLineToRelative(160.0f)
                        verticalLineToRelative(-400.0f)
                        lineTo(400.0f, 440.0f)
                        verticalLineToRelative(400.0f)
                        close()
                        moveTo(400.0f, 840.0f)
                        horizontalLineToRelative(160.0f)
                        horizontalLineToRelative(-160.0f)
                        close()
                    }
                }
                .build()
        return _settingsRemote!!
    }

private var _settingsRemote: ImageVector? = null
