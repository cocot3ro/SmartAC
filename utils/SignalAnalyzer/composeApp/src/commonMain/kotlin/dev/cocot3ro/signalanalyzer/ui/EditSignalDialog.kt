package dev.cocot3ro.signalanalyzer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.cocot3ro.signalanalyzer.domain.model.PulseLevel
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase

@Composable
fun EditSignalDialog(
    signal: SignalBase,
    onDismissRequest: () -> Unit,
    onEditSignal: (Long, String, String, Boolean) -> Unit
) {
    val (name, setName) = remember { mutableStateOf(signal.name) }
    val (rawData, setRawData) = remember {
        mutableStateOf(signal.signalData.joinToString(",") {
            when (it.pulseLevel) {
                PulseLevel.LOW -> "-"
                PulseLevel.HIGH -> "+"
            } + it.duration
        })
    }
    val (compare, setCompare) = remember { mutableStateOf(signal.compare) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Add Signal") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = setName,
                    label = { Text("Name") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rawData,
                    onValueChange = setRawData,
                    maxLines = 10,
                    label = { Text("Raw Data") }
                )


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = compare,
                        onCheckedChange = setCompare
                    )

                    Text(
                        modifier = Modifier.clickable { setCompare(!compare) },
                        text = "Compare"
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = name.isNotBlank() && rawData.isNotBlank(),
                onClick = onClick@{
                    onEditSignal(signal.id, name, rawData.trim(), compare)
                    onDismissRequest()
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        }
    )
}