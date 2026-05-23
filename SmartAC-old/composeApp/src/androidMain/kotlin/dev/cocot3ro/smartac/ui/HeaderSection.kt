package dev.cocot3ro.smartac.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import smartac.composeapp.generated.resources.Res
import smartac.composeapp.generated.resources.connecting
import smartac.composeapp.generated.resources.connection_error
import smartac.composeapp.generated.resources.connection_error_detail
import smartac.composeapp.generated.resources.unexpected_error

@Composable
fun HeaderSection(
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
            ErrorCard(
                title = "Device is offline.",
                description = "Please turn on the AC unit."
            )
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