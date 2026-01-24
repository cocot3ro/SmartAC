package dev.cocot3ro.smartac.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val ANGLE: Float = 270f
private const val ANGLE_HALF: Float = ANGLE * 0.5f
private const val PI_180: Double = PI / 180f

// TODO: Create a State and `rememberCircularTempIndicatorState` for loading, error, etc.
@Composable
fun CircularTempIndicator(
    stroke: Float,
    circleRadius: Float,
    progressColor: Color,
    backgroundColor: Color,
    minValue: Float,
    maxValue: Float,
    value: Float?,
    modifier: Modifier = Modifier
) {
    var width: Int by remember { mutableIntStateOf(value = 0) }
    var height: Int by remember { mutableIntStateOf(value = 0) }
    var radius: Float by remember { mutableFloatStateOf(value = 0f) }
    var center: Offset by remember { mutableStateOf(value = Offset.Zero) }

    val animatedValue: Float by animateFloatAsState(
        targetValue = ((value ?: minValue).coerceIn(
            minimumValue = minValue,
            maximumValue = maxValue
        ) - minValue) / (maxValue - minValue),
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = LinearEasing
        )
    )

    val appliedAngle: Float = animatedValue * ANGLE

    val offset = Offset(
        x = radius * cos(x = (ANGLE_HALF + appliedAngle) * PI_180).toFloat(),
        y = radius * sin(x = (ANGLE_HALF + appliedAngle) * PI_180).toFloat()
    )

    Canvas(
        modifier = modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .padding(top = 12.dp)
            .onGloballyPositioned { coordinates: LayoutCoordinates ->
                width = coordinates.size.width
                height = coordinates.size.height
                center = Offset(x = width * 0.5f, y = height * 0.5f)
                radius = min(a = width, b = height) * 0.5f - stroke * 0.5f
            }
    ) {
        drawArc(
            color = backgroundColor,
            startAngle = ANGLE_HALF,
            sweepAngle = ANGLE,
            topLeft = center - Offset(x = radius, y = radius),
            size = Size(width = radius * 2, height = radius * 2),
            useCenter = false,
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        drawArc(
            color = progressColor,
            startAngle = ANGLE_HALF,
            sweepAngle = appliedAngle,
            topLeft = center - Offset(x = radius, y = radius),
            size = Size(width = radius * 2, height = radius * 2),
            useCenter = false,
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        drawCircle(
            color = Color.Transparent,
            radius = circleRadius * 1.5f,
            center = center + offset,
            blendMode = BlendMode.Clear
        )

        drawCircle(
            color = progressColor,
            radius = circleRadius,
            center = center + offset
        )
    }
}
