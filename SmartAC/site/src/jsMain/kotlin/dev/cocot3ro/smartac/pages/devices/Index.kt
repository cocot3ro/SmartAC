package dev.cocot3ro.smartac.pages.devices

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.layouts.PageLayoutData

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Devices"))
}

@Suppress("ModifierRequired")
@Page
@Composable
fun DevicesPage() {
    SpanText("Devices")
}
