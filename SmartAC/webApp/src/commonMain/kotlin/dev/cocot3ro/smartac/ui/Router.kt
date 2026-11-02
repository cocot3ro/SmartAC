package dev.cocot3ro.smartac.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.cocot3ro.smartac.ui.context.AuthContext
import dev.cocot3ro.smartac.ui.context.LocalAuthContext
import dev.cocot3ro.smartac.ui.screen.dashboard.DashboardTab
import dev.cocot3ro.smartac.ui.screen.devices.DevicesTab
import dev.cocot3ro.smartac.ui.screen.login.LoginScreen
import dev.cocot3ro.smartac.ui.screen.setup.SetupScreen
import dev.cocot3ro.smartac.ui.screen.unhealthy.UnhealthyScreen
import dev.cocot3ro.smartac.ui.screen.users.UsersTab
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ParamsComparedByRef")
@Composable
fun Router(
    modifier: Modifier = Modifier,
    authContext: AuthContext = LocalAuthContext.current,
    viewModel: RouterViewModel = koinViewModel()
) {
    val healthState: HealthState = viewModel.health.collectAsStateWithLifecycle().value

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (healthState) {
                HealthState.Idle,
                HealthState.Loading -> {
                    return@Box Loading(modifier = Modifier.align(Alignment.Center))
                }

                is HealthState.Success -> if (!healthState.data.setup) {
                    return@Box SetupScreen(modifier = Modifier.fillMaxSize())
                }

                is HealthState.Error -> {
                    return@Box UnhealthyScreen(modifier = Modifier.fillMaxSize())
                }
            }

            if (authContext.isAuthenticated == null) {
                return@Box Loading(modifier = Modifier.align(Alignment.Center))
            } else if (!authContext.isAuthenticated) {
                return@Box LoginScreen(modifier = Modifier.fillMaxSize())
            }

            TabNavigator(tab = DashboardTab) { navigator: TabNavigator ->
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Row(modifier = Modifier.background(Color.Red)) {
                                TabNavigationItem(DashboardTab)
                                TabNavigationItem(UsersTab)
                                TabNavigationItem(DevicesTab)
                            }
                        })
                    }
                ) { innerPadding: PaddingValues ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        CurrentTab()
                    }
                }
            }
        }
    }
}

@Suppress("ParamsComparedByRef")
@Composable
private fun RowScope.TabNavigationItem(
    tab: Tab,
    tabNavigator: TabNavigator = LocalTabNavigator.current
) {
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon: Painter ->
                Icon(painter = icon, contentDescription = tab.options.title)
            }
        },
        label = {
            Text(text = tab.options.title)
        }
    )
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            4.dp,
            Alignment.CenterVertically
        )
    ) {
        Text(text = "Loading ...")
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.25f))
    }
}
