package dev.cocot3ro.signalanalyzer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.cocot3ro.signalanalyzer.domain.model.SignalType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddSignalDialog(
    onDismissRequest: () -> Unit,
    onAddSignal: (SignalType, String, String, Boolean) -> Unit
) {
    var type by remember { mutableStateOf<SignalType?>(null) }
    val (name, setName) = remember { mutableStateOf("") }
    val (rawData, setRawData) = remember { mutableStateOf("") }
    val (compare, setCompare) = remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Add Signal") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    SignalType.entries.forEach { signalType ->
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = type == signalType,
                                onClick = { type = signalType },
                            )

                            Text(
                                modifier = Modifier.clickable { type = signalType },
                                text = signalType.name
                            )
                        }
                    }
                }

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
                enabled = type != null && name.isNotBlank() && rawData.isNotBlank(),
                onClick = onClick@{
                    onAddSignal(type ?: return@onClick, name, rawData.trim(), compare)
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