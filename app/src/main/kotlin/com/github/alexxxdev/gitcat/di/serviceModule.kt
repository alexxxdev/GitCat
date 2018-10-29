package com.github.alexxxdev.gitcat.di

import com.github.alexxxdev.gitcat.data.Authenticator
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val serviceModule: Module = module {
    single { Authenticator(get()) }
}