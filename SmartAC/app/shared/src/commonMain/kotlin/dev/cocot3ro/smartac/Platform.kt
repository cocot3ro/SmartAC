package dev.cocot3ro.smartac

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform