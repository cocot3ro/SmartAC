package dev.cocot3ro.signalanalyzer.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.cocot3ro.signalanalyzer.domain.model.SignalBase

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit,
    signal: SignalBase
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Text(text = "Estás seguro de que deseas eliminar la señal \"${signal.name}\"?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDelete()
                    onDismissRequest()
                }
            ) {
                Text(text = "Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancelar")
            }
        }
    )

}