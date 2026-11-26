package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Ul

@Composable
@Suppress("ModifierRequired")
fun SiteMenu() {
    Header(attrs = {
        classes("navbar-expand-md")
    }) {
        Div(attrs = {
            id("navbar-menu")
            classes("collapse", "navbar-collapse")
        }) {
            Div(attrs = {
                classes("navbar")
            }) {
                Div(attrs = {
                    classes("container-xl")
                }) {
                    Div(attrs = {
                        classes(
                            "row",
                            "flex-column",
                            "flex-md-row",
                            "flex-fill",
                            "align-items-center"
                        )
                    }) {
                        Div(attrs = {
                            classes("col")
                        }) {
                            Ul(attrs = {
                                classes("navbar-nav")
                            }) {

                            }
                        }
                    }
                }
            }
        }
    }
}
