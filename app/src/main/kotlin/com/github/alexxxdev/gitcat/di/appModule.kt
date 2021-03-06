package com.github.alexxxdev.gitcat.di

import com.github.alexxxdev.gitcat.data.GraphQLRepository
import com.github.alexxxdev.gitcat.data.UserRepository
import com.github.alexxxdev.gitcat.ui.Navigator
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule: Module = module {
    single { Navigator() }
    single { GraphQLRepository(get()) }
    single { UserRepository(get()) }
}