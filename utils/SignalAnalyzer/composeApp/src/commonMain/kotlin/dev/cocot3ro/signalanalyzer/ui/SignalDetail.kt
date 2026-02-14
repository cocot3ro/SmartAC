package dev.cocot3ro.signalanalyzer.ui

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase
import dev.cocot3ro.signalanalyzer.domain.model.SignalType
import dev.cocot3ro.signalanalyzer.ui.icons.NestDisplay
import dev.cocot3ro.signalanalyzer.ui.icons.RawOff
import dev.cocot3ro.signalanalyzer.ui.icons.RawOn
import dev.cocot3ro.signalanalyzer.ui.icons.SettingsRemote
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalFoundationApi::class)
@Composable
fun SignalDetail(
    modifier: Modifier,
    list: List<SignalBase>,
    selectedType: SignalType?,
    onSelectType: (SignalType) -> Unit,
    diffList: List<Int>,
    onAdd: () -> Unit,
    onDeleteAll: () -> Unit,
    onEdit: (SignalBase) -> Unit,
    onDelete: (SignalBase) -> Unit,
    onCompare: (SignalBase) -> Unit,
    onCompareAll: (Boolean) -> Unit,
) {

    Column(modifier = modifier) {
        var displayRaw: Boolean by remember { mutableStateOf(false) }

        Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {

            listOf(
                SignalType.IR to Icons.SettingsRemote,
                SignalType.DISPLAY to Icons.NestDisplay
            ).forEach { (type, icon) ->
                IconToggleButton(
                    checked = selectedType == type,
                    onCheckedChange = {
                        if (it) onSelectType(type)
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }

            VerticalDivider()

            listOf(
                onAdd to Icons.Default.Add,
                onDeleteAll to Icons.Default.DeleteForever,
                { displayRaw = !displayRaw } to if (displayRaw) Icons.RawOn else Icons.RawOff
            ).forEach { (action, icon) ->
                IconButton(onClick = action) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }

            val compareSet = list.map(SignalBase::compare).toSet()
            val compareState = when {
                compareSet.isEmpty() -> null
                compareSet.size > 1 -> ToggleableState.Indeterminate
                compareSet.first() -> ToggleableState.On
                compareSet.first().not() -> ToggleableState.Off
                else -> null
            }

            compareState?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TriStateCheckbox(
                        state = compareState,
                        onClick = {
                            when (compareState) {
                                ToggleableState.On -> onCompareAll(false)
                                ToggleableState.Indeterminate,
                                ToggleableState.Off -> onCompareAll(true)
                            }
                        }
                    )

                    Text(text = "Compare")
                }
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(all = 8.dp)
        ) {
            items(list) { signal: SignalBase ->
                ContextMenuArea(
                    items = {
                        listOf(
                            ContextMenuItem(if (signal.compare) "Uncompare" else "Compare") {
                                onCompare(signal)
                            },
                            ContextMenuItem("Edit") {
                                onEdit(signal)
                            },
                            ContextMenuItem("Delete") {
                                onDelete(signal)
                            }
                        )
                    },
                    content = {
                        signal.Render(
                            modifier = Modifier.fillMaxSize(),
                            diffList = diffList,
                            displayRaw = displayRaw
                        )
                    }
                )
            }
        }
    }
}
