package dev.cocot3ro.smartac.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("dev.cocot3ro.smartac.domain.usecase")
object UseCaseModule