package dev.cocot3ro.smartac.localization

import io.github.skeptick.libres.LibresLocale


val supportedLocales = arrayOf(
    "en" to "English",
    "es" to "Español"
)

val defaultLocale: LibresLocale = LibresLocale(
    language = supportedLocales.first().first,
    script = null,
    region = null
)
