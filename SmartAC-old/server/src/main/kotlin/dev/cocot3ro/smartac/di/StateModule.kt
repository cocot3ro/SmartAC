package dev.cocot3ro.smartac.di

import dev.cocot3ro.smartac.core.state.StateRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val stateModule: Module = module {
    single<StateRepository>()
}