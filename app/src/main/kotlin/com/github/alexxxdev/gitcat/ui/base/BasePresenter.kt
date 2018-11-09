package com.github.alexxxdev.gitcat.ui.base

import androidx.annotation.CallSuper
import com.github.alexxxdev.gitcat.data.AuthRepository
import com.github.alexxxdev.gitcat.data.GraphQLRepository
import com.github.alexxxdev.gitcat.ui.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.grandcentrix.thirtyinch.TiConfiguration
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

val PRESENTER_CONFIG = TiConfiguration.Builder()
        .setRetainPresenterEnabled(true)
        .setCallOnMainThreadInterceptorEnabled(true)
        .setDistinctUntilChangedInterceptorEnabled(true)
        .build()

open class BasePresenter<V : BaseContract.View> : TiPresenter<V>(PRESENTER_CONFIG), BaseContract.Presenter, KoinComponent {

    protected val authRepository by inject<AuthRepository>()
    protected val graphQLRepository by inject<GraphQLRepository>()
    protected val navigator by inject<Navigator>()

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

    fun gotoLogin() {
        navigator.navigateToLogin(true)
    }

    fun checkUserInfo() {
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.Default) {
                graphQLRepository.getUserInfo(authRepository.login ?: "")
            }.await().fold({
                if (it.errors.isEmpty()) {
                    deliverToView { onInitSuccess() }
                } else {
                    deliverToView { onInitError(it.errors.first().message) }
                }
            }, { error ->
                deliverToView { onInitError(error.exception.localizedMessage) }
            })
        }
    }
}
