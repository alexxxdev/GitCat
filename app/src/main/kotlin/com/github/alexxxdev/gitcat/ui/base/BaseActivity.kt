package com.github.alexxxdev.gitcat.ui.base

import android.os.Bundle
import com.github.alexxxdev.gitcat.ui.Navigator
import net.grandcentrix.thirtyinch.TiActivity
import org.koin.android.ext.android.inject

abstract class BaseActivity<V : BaseContract.View, P : BasePresenter<V>> : TiActivity<P, V>(), BaseContract.View {
    abstract val layoutId: Int

    protected val navigator by inject<Navigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.setContext(this)
        setContentView(layoutId)
        if (savedInstanceState == null) {
            presenter.checkGitHubStatus()
        }
        if (!validateAuth()) return
    }

    override fun onResume() {
        super.onResume()
        navigator.setContext(this)
    }

    private fun validateAuth(): Boolean {
        if (isCheckedAuth()) {
            if (!isLoggedIn()) {
                onRequireLogin()
                return false
            }
        }
        return true
    }

    private fun isLoggedIn(): Boolean = presenter.isLoggedIn()

    private fun onRequireLogin() {
        presenter.requireLogin { presenter.gotoLogin() }
    }

    protected open fun isCheckedAuth(): Boolean = true
}