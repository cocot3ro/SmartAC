package dev.cocot3ro.smartac.di

import org.koin.core.annotation.KoinApplication

@KoinApplication(
    modules = [
        UiModule::class,
        AppModule::class,
        UseCaseModule::class,
        NetworkModule::class,
        DatastoreModule::class
    ]
)
object KoinDi