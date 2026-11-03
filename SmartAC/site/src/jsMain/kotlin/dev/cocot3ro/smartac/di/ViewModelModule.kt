package dev.cocot3ro.smartac.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@ComponentScan("dev.cocot3ro.smartac.viewmodel")
@Configuration
object ViewModelModule