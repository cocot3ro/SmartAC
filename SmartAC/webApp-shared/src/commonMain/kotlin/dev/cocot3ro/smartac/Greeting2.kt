package dev.cocot3ro.smartac

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
class Greeting2 {

    fun greet(): String {
        return "Hello from Kotlin"
    }
}
