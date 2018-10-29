package com.github.alexxxdev.gitcat.di

import com.github.alexxxdev.gitcat.data.AuthRepository
import com.github.alexxxdev.gitcat.data.GithubAuthClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val authModule: Module = module {
    single { GithubAuthClient() }
    single { AuthRepository(get(), get()) }
}