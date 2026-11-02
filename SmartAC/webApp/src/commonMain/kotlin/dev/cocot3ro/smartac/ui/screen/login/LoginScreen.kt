package dev.cocot3ro.smartac.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import dev.cocot3ro.smartac.ui.context.AuthContext
import dev.cocot3ro.smartac.ui.context.LocalAuthContext

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authContext: AuthContext = LocalAuthContext.current
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Login screen", fontSize = 24.sp)

        TextButton(
            onClick = {
                authContext.login("")
            }
        ) {
            Text("Login")
        }
    }
}
