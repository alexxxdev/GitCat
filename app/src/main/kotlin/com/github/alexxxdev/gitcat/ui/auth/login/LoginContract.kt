package com.github.alexxxdev.gitcat.ui.auth.login

import com.github.alexxxdev.gitcat.ui.base.BaseContract
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread

interface LoginContract : BaseContract {
    interface View : BaseContract.View {
        @CallOnMainThread
        fun onSuccess()

        @CallOnMainThread
        // @DistinctUntilChanged
        fun onError(message: String)

        @CallOnMainThread
        fun onNeedCode2FA()
    }

    interface Presenter : BaseContract.Presenter {
        fun login(login: String, password: String)
        fun send2FACode(code: String)
    }
}