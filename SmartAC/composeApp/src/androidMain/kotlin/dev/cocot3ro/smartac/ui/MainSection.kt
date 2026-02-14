package dev.cocot3ro.smartac.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainSection(
    uiState: UiState,
    onTempMinusClick: () -> Unit,
    onTempPlusClick: () -> Unit,
    onPowerChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val (min, max) = 16f to 30f

        val value = when (uiState) {
            is UiState.Success -> uiState.state.targetTemp.toFloat()
            else -> null
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(visible = uiState is UiState.Success) {
                    (uiState as? UiState.Success)?.let { uiState: UiState.Success ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.DeviceThermostat,
                                    contentDescription = null
                                )

                                Text(
                                    text = "${uiState.state.currentTemp}",
                                    fontSize = 20.sp
                                )
                            }

                            Text(text = "Ambiente")
                        }
                    }
                }

                AnimatedVisibility(visible = uiState is UiState.Success) {
                    (uiState as? UiState.Success)?.let { uiState: UiState.Success ->
                        FilledIconToggleButton(
                            checked = uiState.state.power,
                            onCheckedChange = onPowerChange,
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.PowerSettingsNew,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }

            CircularTempIndicator(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                stroke = 10f,
                circleRadius = 32f,
                minValue = min,
                maxValue = max,
                value = value,
                progressColor = Color.White,
                backgroundColor = Color.Gray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (uiState is UiState.Success) {
                    FloatingActionButton(
                        onClick = onTempMinusClick,
                        shape = CircleShape,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = null
                        )
                    }
                }

                Text(
                    fontSize = 40.sp,
                    text = when (uiState) {
                        is UiState.Success -> "${uiState.state.targetTemp}"

                        else -> "--"
                    }
                )

                if (uiState is UiState.Success) {
                    FloatingActionButton(
                        onClick = onTempPlusClick,
                        shape = CircleShape,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                }
            }

            if (uiState is UiState.Success) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 36.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "${min.toInt()}ºC",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "${max.toInt()}ºC",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
