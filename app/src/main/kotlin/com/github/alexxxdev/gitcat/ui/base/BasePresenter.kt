package com.github.alexxxdev.gitcat.ui.base

import androidx.annotation.CallSuper
import com.github.alexxxdev.gitcat.data.AuthRepository
import net.grandcentrix.thirtyinch.TiConfiguration
import net.grandcentrix.thirtyinch.TiPresenter
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

val PRESENTER_CONFIG = TiConfiguration.Builder()
        .setRetainPresenterEnabled(true)
        .setCallOnMainThreadInterceptorEnabled(true)
        .setDistinctUntilChangedInterceptorEnabled(true)
        .build()

open class BasePresenter<V : BaseContract.View> : TiPresenter<V>(PRESENTER_CONFIG), BaseContract.Presenter, KoinComponent {

    val authRepository: AuthRepository by inject()

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    fun checkGitHubStatus() {
    }

    fun requireLogin(block: () -> Unit) {
        authRepository.logout()
        block()
    }

    fun isLoggedIn() = authRepository.isLoggedIn()
}
