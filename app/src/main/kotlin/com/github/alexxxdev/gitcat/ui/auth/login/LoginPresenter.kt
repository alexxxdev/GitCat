package com.github.alexxxdev.gitcat.ui.auth.login

import com.github.alexxxdev.gitcat.data.Need2FAException
import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    val job = Job()

    override fun login(login: String, password: String) {
        GlobalScope.launch(Dispatchers.Main + job) {
            authRepository.login(login, password)
                    .fold({
                        deliverToView { onSuccess() }
                    }, { error ->
                        if (error.exception is Need2FAException) {
                            deliverToView { onNeedCode2FA() }
                        } else {
                            deliverToView { onError(error.message) }
                        }
                    })
        }
    }

    override fun send2FACode(code: String) {
        GlobalScope.launch(Dispatchers.Main + job) {
            authRepository.send2FACode(code)
                    .fold({
                        deliverToView { onSuccess() }
                    }, { error ->
                        deliverToView { onError(error.message) }
                    })
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun gotoHome() {
        navigator.navigateToHome(true)
    }
}