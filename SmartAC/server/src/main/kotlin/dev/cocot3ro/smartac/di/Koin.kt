package dev.cocot3ro.smartac.di

import org.koin.core.KoinApplication
import org.koin.ksp.generated.module

fun KoinApplication.initKoin() {
    modules(
        NetworkModule.module
    )
}