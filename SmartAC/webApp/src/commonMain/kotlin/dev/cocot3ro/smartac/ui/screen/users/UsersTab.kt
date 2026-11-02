package dev.cocot3ro.smartac.ui.screen.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object UsersTab : Tab {
    override val options: TabOptions
        @Composable get() {
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Users",
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Users")
        }
    }
}
