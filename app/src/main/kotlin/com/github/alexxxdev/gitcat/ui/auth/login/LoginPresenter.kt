package com.github.alexxxdev.gitcat.ui.auth.login

import com.github.alexxxdev.gitcat.data.CODE_AUTH_ERROR
import com.github.alexxxdev.gitcat.data.HEADER_OTP
import com.github.alexxxdev.gitcat.data.REQUIRED_APP
import com.github.alexxxdev.gitcat.data.REQUIRED_SMS
import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    val job = Job()

    override fun login(login: String, password: String) {
        GlobalScope.launch(Dispatchers.Main + job) {
            async(Dispatchers.Default + job) { authRepository.login(login, password) }
                    .await().third
                    .fold({
                        deliverToView { onSuccess() }
                    }, { error ->
                        if (error.response.statusCode == CODE_AUTH_ERROR && error.response.headers.containsKey(HEADER_OTP)) {
                            if (error.response.headers.getValue(HEADER_OTP).find { str -> str == REQUIRED_SMS || str == REQUIRED_APP } != null) {
                                deliverToView { onNeedCode2FA() }
                            } else {
                                deliverToView { onError(error.localizedMessage) }
                            }
                        } else {
                            deliverToView { onError(error.localizedMessage) }
                        }
                    })
        }
    }

    override fun send2FACode(code: String) {
        GlobalScope.launch(Dispatchers.Main + job) {
            async(Dispatchers.Default + job) { authRepository.send2FACode(code) }
                    .await().third
                    .fold({
                        deliverToView { onSuccess() }
                    }, {
                        deliverToView { onError(it.localizedMessage) }
                    })
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}