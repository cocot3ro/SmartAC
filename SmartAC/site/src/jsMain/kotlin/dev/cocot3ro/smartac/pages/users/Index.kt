package dev.cocot3ro.smartac.pages.users

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.layouts.PageLayoutData

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Users"))
}

@Suppress("ModifierRequired")
@Page
@Composable
fun UsersPage() {
    SpanText("Users")
}
