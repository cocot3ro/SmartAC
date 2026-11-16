package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Header

@Composable
fun SiteHeader() {
//    <header className="navbar navbar-expand-md d-print-none">
//      <div className="container-xl">
//          <button
//              className="navbar-toggler"
//              type="button"
//              data-bs-toggle="collapse"
//              data-bs-target="#navbar-menu"
//              aria-controls="navbar-menu"
//              aria-expanded="false"
//              aria-label="Toggle navigation"
//          >
//              <span className="navbar-toggler-icon" />
//          </button>

    Header(attrs = {
        classes("navbar", "navbar-expand-md", "d-print-none")
    }) {
        Div(attrs = {
            classes("container-xl")
        }) {
            Button(attrs = {
                classes("navbar-toggler")
            }) {

            }
        }
    }
}
