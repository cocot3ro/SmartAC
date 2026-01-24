package dev.cocot3ro.smartac.di

import dev.cocot3ro.smartac.core.state.StateRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val stateModule: Module = module {
    singleOf(::StateRepository)
}