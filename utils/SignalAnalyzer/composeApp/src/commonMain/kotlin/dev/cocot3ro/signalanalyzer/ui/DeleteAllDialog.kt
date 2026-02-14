package dev.cocot3ro.signalanalyzer.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteAllDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Delete All Signals") },
        text = { Text("Are you sure you want to delete all signals? This action cannot be undone.") },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text("Delete All")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}