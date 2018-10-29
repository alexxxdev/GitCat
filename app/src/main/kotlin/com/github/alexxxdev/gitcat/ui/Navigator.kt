package com.github.alexxxdev.gitcat.ui

import com.github.alexxxdev.gitcat.ui.auth.AuthActivity
import com.github.alexxxdev.gitcat.ui.base.BaseNavigator
import com.github.alexxxdev.gitcat.ui.main.MainActivity

class Navigator : BaseNavigator() {
    fun navigateToHome(finished: Boolean = false) {
        navigateToActivity<MainActivity>(finished)
    }

    fun navigateToLogin(finished: Boolean = false) {
        navigateToActivity<AuthActivity>(finished)
    }
}