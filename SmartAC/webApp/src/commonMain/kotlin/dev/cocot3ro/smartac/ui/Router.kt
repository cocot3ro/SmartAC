package dev.cocot3ro.smartac.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.cocot3ro.smartac.ui.screen.setup.SetupScreen
import dev.cocot3ro.smartac.ui.screen.unhealthy.UnhealthyScreen
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ParamsComparedByRef")
@Composable
fun Router(
    modifier: Modifier = Modifier,
    viewModel: RouterViewModel = koinViewModel()
) {
    val healthState: HealthState = viewModel.health.collectAsStateWithLifecycle().value

    Box(modifier = modifier) {
        when (healthState) {
            HealthState.Idle,
            HealthState.Loading -> {
                Loading(modifier = Modifier.align(Alignment.Center))
            }

            is HealthState.Success -> if (!healthState.data.setup) {
                return SetupScreen(modifier = Modifier.fillMaxSize())
            }

            is HealthState.Error -> {
                UnhealthyScreen(modifier = Modifier.fillMaxSize())
            }
        }

        when (authContext.state) {
            AuthState.Idle -> {
                Loading(modifier = Modifier.align(Alignment.Center))
            }

            AuthState.Authenticated -> {

            }

            AuthState.Unauthenticated -> {

            }
        }

        // app content

    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            4.dp,
            Alignment.CenterVertically
        )
    ) {
        Text(text = "Loading ...")
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            LinearProgressIndicator(modifier = Modifier.weight(0.5f))
        }
    }
}