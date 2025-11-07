package dev.cocot3ro.signalanalyzer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase
import dev.cocot3ro.signalanalyzer.ui.AddSignalDialog
import dev.cocot3ro.signalanalyzer.ui.DeleteAllDialog
import dev.cocot3ro.signalanalyzer.ui.DeleteDialog
import dev.cocot3ro.signalanalyzer.ui.EditSignalDialog
import dev.cocot3ro.signalanalyzer.ui.SignalDetail
import dev.cocot3ro.signalanalyzer.ui.theme.SignalAnalyzerTheme
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private sealed class DialogType {
    data object Add : DialogType()
    data class Edit(val signal: SignalBase) : DialogType()
    data class Delete(val signal: SignalBase) : DialogType()
    data object DeleteAll : DialogType()
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {
    SignalAnalyzerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val list: List<SignalBase> by viewModel.signalList.collectAsState()

            var showDialog: DialogType? by remember { mutableStateOf(null) }

            SignalDetail(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0xFF202020)),
                list = list,
                selectedType = viewModel.selectedType,
                onSelectType = viewModel::selectType,
                diffList = viewModel.diffList,
                onAdd = { showDialog = DialogType.Add },
                onDeleteAll = { showDialog = DialogType.DeleteAll },
                onEdit = { signal: SignalBase ->
                    showDialog = DialogType.Edit(signal)
                },
                onDelete = { signal: SignalBase ->
                    showDialog = DialogType.Delete(signal)
                },
                onCompare = viewModel::updateCompare,
                onCompareAll = viewModel::updateAllCompare
            )

            when (showDialog) {
                DialogType.Add -> {
                    AddSignalDialog(
                        onDismissRequest = { showDialog = null },
                        onAddSignal = viewModel::createSignal
                    )
                }

                is DialogType.Edit -> {
                    EditSignalDialog(
                        signal = (showDialog as DialogType.Edit).signal,
                        onDismissRequest = { showDialog = null },
                        onEditSignal = { id, name, rawData, compare ->
                            viewModel.updateSignal(id, name, rawData, compare)
                            showDialog = null
                        }
                    )
                }

                is DialogType.Delete -> {
                    DeleteDialog(
                        signal = (showDialog as DialogType.Delete).signal,
                        onDismissRequest = { showDialog = null },
                        onDelete = {
                            viewModel.deleteSignal((showDialog as DialogType.Delete).signal)
                            showDialog = null
                        }
                    )
                }

                DialogType.DeleteAll -> {
                    DeleteAllDialog(
                        onDismissRequest = { showDialog = null },
                        onDelete = {
                            viewModel.deleteAll()
                            showDialog = null
                        }
                    )
                }

                null -> Unit
            }
        }
    }
}
