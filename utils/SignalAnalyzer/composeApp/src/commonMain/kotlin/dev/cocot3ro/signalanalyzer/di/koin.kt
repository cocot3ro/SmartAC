package dev.cocot3ro.signalanalyzer.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.cocot3ro.signalanalyzer.AppViewModel
import dev.cocot3ro.signalanalyzer.data.database.AppDatabaseRepository
import dev.cocot3ro.signalanalyzer.database.AppDatabase
import dev.cocot3ro.signalanalyzer.domain.usecases.ManageSignalsUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(module {

            singleOf(::ManageSignalsUseCase)

            viewModelOf(::AppViewModel)

            singleOf(::AppDatabaseRepository)

            single<JdbcSqliteDriver> {
                JdbcSqliteDriver(
                    url = "jdbc:sqlite:signal_analyzer.db",
                    schema = AppDatabase.Schema
                )
            }
        })
    }
}