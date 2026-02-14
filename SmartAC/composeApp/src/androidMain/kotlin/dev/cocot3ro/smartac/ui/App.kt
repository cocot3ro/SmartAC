package dev.cocot3ro.smartac.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SettingsRemote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.cocot3ro.smartac.domain.model.AcControl
import dev.cocot3ro.smartac.domain.model.AcMode
import dev.cocot3ro.smartac.domain.model.FanSpeed
import dev.cocot3ro.smartac.ui.icons.CoolToDry
import dev.cocot3ro.smartac.ui.icons.ModeCool
import dev.cocot3ro.smartac.ui.icons.ModeFan
import dev.cocot3ro.smartac.ui.icons.ModeHeat
import dev.cocot3ro.smartac.ui.icons.ModeHeatCool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

private data class FabMenuItem(
    val icon: @Composable () -> Unit,
    val label: @Composable () -> Unit,
    val onClick: suspend () -> Unit
)

private enum class FabMenuType {
    MENU,
    FAN,
    MODE
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel()
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            var fabMenuType: FabMenuType? by remember { mutableStateOf(null) }
            BackHandler(enabled = fabMenuType != null) { fabMenuType = null }

            FloatingActionButtonMenu(
                expanded = fabMenuType != null,
                button = {
                    TooltipBox(
                        positionProvider =
                            TooltipDefaults.rememberTooltipPositionProvider(
                                if (fabMenuType != null) {
                                    TooltipAnchorPosition.Start
                                } else {
                                    TooltipAnchorPosition.Above
                                }
                            ),
                        tooltip = { PlainTooltip { Text("Toggle menu") } },
                        state = rememberTooltipState(),
                    ) {
                        ToggleFloatingActionButton(
                            modifier =
                                Modifier
                                    .semantics {
                                        traversalIndex = -1f
                                        contentDescription = "Toggle menu"
                                        stateDescription =
                                            if (fabMenuType != null) "Expanded" else "Collapsed"
                                    }
                                    .animateFloatingActionButton(
                                        visible = true,
                                        alignment = Alignment.BottomEnd,
                                    ),
                            checked = fabMenuType != null,
                            onCheckedChange = { checked ->
                                fabMenuType = FabMenuType.MENU.takeIf { checked }
                            },
                        ) {
                            val imageVector: ImageVector by remember {
                                derivedStateOf {
                                    if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.SettingsRemote
                                }
                            }
                            Icon(
                                painter = rememberVectorPainter(imageVector),
                                contentDescription = null,
                                modifier = Modifier.animateIcon({ checkedProgress }),
                            )
                        }
                    }
                },
            ) {
                val items: List<FabMenuItem> = when (fabMenuType) {
                    FabMenuType.MENU -> remember {
                        listOf(
                            FabMenuItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.ModeFan,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = "Fan") },
                                onClick = {
                                    fabMenuType = null
                                    delay(50)
                                    fabMenuType = FabMenuType.FAN
                                }
                            ),

                            FabMenuItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.ModeHeatCool,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = "Mode") },
                                onClick = {
                                    fabMenuType = null
                                    delay(50)
                                    fabMenuType = FabMenuType.MODE
                                }
                            )
                        )
                    }

                    FabMenuType.FAN -> remember {
                        listOf(
                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Auto") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setFanSpeed(AcControl.FanSpeed.AUTO)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "High") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setFanSpeed(AcControl.FanSpeed.HIGH)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Medium") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setFanSpeed(AcControl.FanSpeed.MEDIUM)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Low") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setFanSpeed(AcControl.FanSpeed.LOW)
                                }
                            )
                        )
                    }

                    FabMenuType.MODE -> remember {
                        listOf(
                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Cool") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setMode(AcControl.Mode.COOL)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Dry") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setMode(AcControl.Mode.DRY)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Fan") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setMode(AcControl.Mode.FAN)
                                }
                            ),

                            FabMenuItem(
                                icon = { },
                                label = { Text(text = "Heat") },
                                onClick = {
                                    fabMenuType = null
                                    viewModel.setMode(AcControl.Mode.HEAT)
                                }
                            )
                        )
                    }

                    null -> emptyList()
                }

                val coroutineScope: CoroutineScope = rememberCoroutineScope()

                items.forEachIndexed { i, (icon, label, action) ->
                    FloatingActionButtonMenuItem(
                        modifier =
                            Modifier.semantics {
                                isTraversalGroup = true
                                // Add a custom a11y action to allow closing the menu when focusing
                                // the last menu item, since the close button comes before the first
                                // menu item in the traversal order.
                                if (i == items.size - 1) {
                                    customActions = listOf(
                                        CustomAccessibilityAction(
                                            label = "Close menu",
                                            action = action@{
                                                fabMenuType = null
                                                return@action true
                                            }
                                        )
                                    )
                                }
                            },
                        onClick = {
                            coroutineScope.launch {
                                action()
                            }
                        },
                        icon = { icon.invoke() },
                        text = { label.invoke() },
                    )
                }
            }
        }
    ) { paddingValues ->

        val uiState: UiState by viewModel.state.collectAsStateWithLifecycle()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val listState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
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

                item(contentType = "main") {
                    MainSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        uiState = uiState,
                        onTempMinusClick = viewModel::tempDecrease,
                        onTempPlusClick = viewModel::tempIncrease,
                        onPowerChange = { power: Boolean ->
                            viewModel.setPower(
                                if (power) AcControl.Power.POWER_ON
                                else AcControl.Power.POWER_OFF
                            )
                        }
                    )
                }

                (uiState as? UiState.Success)?.state?.let { acState ->
                    item(contentType = "info") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedCard(modifier = Modifier.weight(1f)) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Mode",
                                        fontSize = 22.sp
                                    )

                                    HorizontalDivider()

                                    val (icon: ImageVector, label: String) = when (acState.mode) {
                                        AcMode.COOL -> Icons.ModeCool to "Cool"
                                        AcMode.HEAT -> Icons.ModeHeat to "Heat"
                                        AcMode.DRY -> Icons.CoolToDry to "Dry"
                                        AcMode.FAN -> Icons.ModeFan to "Fan"
                                    }

                                    Row(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(32.dp),
                                            imageVector = icon,
                                            contentDescription = null
                                        )
                                        Text(
                                            text = label,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }

                            OutlinedCard(modifier = Modifier.weight(1f)) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Fan Speed",
                                        fontSize = 22.sp
                                    )

                                    HorizontalDivider()

                                    val label: String = when (acState.fanSpeed) {
                                        FanSpeed.LOW -> "Low"
                                        FanSpeed.MEDIUM -> "Medium"
                                        FanSpeed.HIGH -> "High"
                                        FanSpeed.STOP -> "Off"
                                    }

                                    Row(
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .height(32.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        if (acState.isFanAuto) {
                                            Text(
                                                text = "Auto",
                                                fontSize = 18.sp
                                            )

                                            VerticalDivider()
                                        }
                                        Text(
                                            text = label,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
