package dev.cocot3ro.smartac.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartac.composeapp.generated.resources.Res
import smartac.composeapp.generated.resources.connecting
import smartac.composeapp.generated.resources.connection_error
import smartac.composeapp.generated.resources.connection_error_detail
import smartac.composeapp.generated.resources.unexpected_error

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel()
) {
    Scaffold(modifier = modifier) { paddingValues ->

        val uiState: UiState by viewModel.state.collectAsStateWithLifecycle()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(contentType = "header") {
                HeaderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    uiState = uiState
                )
            }

            item(contentType = "temp") {
                TempSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    uiState = uiState,
                    onTempMinusClick = viewModel::tempDecrease,
                    onTempPlusClick = viewModel::tempIncrease
                )
            }
        }
    }
}

@Composable
private fun HeaderSection(
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AnimatedVisibility(visible = uiState is UiState.Loading) {
            Card(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(resource = Res.string.connecting)
                )
            }
        }

        AnimatedVisibility(visible = uiState is UiState.ConnectionError) {
            ErrorCard(
                title = stringResource(resource = Res.string.connection_error),
                description = stringResource(resource = Res.string.connection_error_detail)
            )
        }

        AnimatedVisibility(visible = uiState is UiState.DeviceOffline) {
            (uiState as? UiState.DeviceOffline)?.let { _: UiState.DeviceOffline ->
                ErrorCard(
                    title = stringResource(resource = Res.string.unexpected_error),
                    description = "Device is offline. Please turn on the AC unit."
                )
            }
        }

        AnimatedVisibility(visible = uiState is UiState.Error) {
            (uiState as? UiState.Error)?.let { uiState: UiState.Error ->
                ErrorCard(
                    title = stringResource(resource = Res.string.unexpected_error),
                    description = uiState.message
                )
            }
        }
    }
}

@Composable
private fun TempSection(
    uiState: UiState,
    onTempMinusClick: () -> Unit,
    onTempPlusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val (min, max) = 16f to 30f

        val value = when (uiState) {
            is UiState.Success -> uiState.state.targetTemp.toFloat()
            else -> min
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 0.dp, end = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
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
                }

                Column {
                    AnimatedVisibility(visible = uiState is UiState.Success) {
                        Icon(
                            imageVector = Icons.Filled.PowerSettingsNew,
                            contentDescription = null
                        )
                    }
                }
            }

            CircularTempIndicator(
                modifier = Modifier
                    .padding(top = 4.dp)
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

//    when (val uiState: UiState = viewModel.state.collectAsStateWithLifecycle().value) {
//        is UiState.Idle -> Text(text = "Idle")
//        is UiState.Loading -> Text(text = "Loading...")
//        is UiState.ConnectionError -> Text(
//            text = "Connection Error: ${uiState.message}",
//            color = MaterialTheme.colorScheme.error
//        )
//
//        is UiState.Error -> Text(

//            text = "Error: ${uiState.message}",
//            color = MaterialTheme.colorScheme.error
//        )
//
//        is UiState.Success -> uiState.acState.let { state ->
//            Text(text = state.toString())
//            state.errorCode?.let { err ->
//                Text(
//                    text = "Error Code: 0x${err.toString(radix = 16).padStart(2, '0')}",
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//
//            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
//
//            Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
//                Text(
//                    text = "Power",
//                    fontSize = 22.sp,
//                    textDecoration = TextDecoration.Underline
//                )
//
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    AcControl.Power.entries.forEach { power: AcControl.Power ->
//                        Button(
//                            modifier = Modifier.weight(weight = 1f),
//                            onClick = { viewModel.setPower(power) }
//                        ) {
//                            Text(text = power.name.replace("POWER", "PWR"))
//                        }
//                    }
//                }
//
//                Text(
//                    text = "Mode",
//                    fontSize = 22.sp,
//                    textDecoration = TextDecoration.Underline
//                )
//
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    AcControl.Mode.entries.forEach { mode: AcControl.Mode ->
//                        Button(
//                            modifier = Modifier.weight(weight = 1f),
//                            onClick = { viewModel.setMode(mode) }
//                        ) {
//                            Text(text = mode.name)
//                        }
//                    }
//                }
//
//                Text(
//                    text = "Fan Speed",
//                    fontSize = 22.sp,
//                    textDecoration = TextDecoration.Underline
//                )
//
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    AcControl.FanSpeed.entries.forEach { fanSpeed: AcControl.FanSpeed ->
//                        Button(
//                            modifier = Modifier.weight(weight = 1f),
//                            onClick = { viewModel.setFanSpeed(fanSpeed) }
//                        ) {
//                            if (fanSpeed == AcControl.FanSpeed.MEDIUM) {
//                                Text(text = "MED")
//                            } else {
//                                Text(text = fanSpeed.name)
//                            }
//                        }
//                    }
//                }
//
//                Text(
//                    text = "Temperature",
//                    fontSize = 22.sp,
//                    textDecoration = TextDecoration.Underline
//                )
//
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    Button(
//                        modifier = Modifier.weight(weight = 1f),
//                        onClick = viewModel::tempDecrease
//                    ) {
//                        Text(text = "Decrease")
//                    }
//                    Button(
//                        modifier = Modifier.weight(weight = 1f),
//                        onClick = viewModel::tempIncrease
//                    ) {
//                        Text(text = "Increase")
//                    }
//                }
//
//                var value by remember { mutableFloatStateOf(state.targetTemp.toFloat()) }
//
//                Slider(
//                    steps = 30 - 16 - 1,
//                    value = value,
//                    onValueChange = { value = it },
//                    onValueChangeFinished = {
//                        viewModel.setTemperature(value.toInt())
//                    },
//                    valueRange = 16f..30f
//                )
//            }
//        }
//    }
