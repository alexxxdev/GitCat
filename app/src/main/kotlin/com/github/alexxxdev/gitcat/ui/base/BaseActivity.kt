package com.github.alexxxdev.gitcat.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import com.github.alexxxdev.gitcat.ui.Navigator
import net.grandcentrix.thirtyinch.TiActivity
import org.koin.android.ext.android.inject

abstract class BaseActivity<V : BaseContract.View, P : BasePresenter<V>> : TiActivity<P, V>(), BaseContract.View {
    protected abstract val layoutId: Int
    private var savedInstanceState: Bundle? = null

    protected val navigator by inject<Navigator>()

    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        navigator.setContext(this)
        if (savedInstanceState == null) {
            presenter.checkGitHubStatus()
        }
        if (!validateAuth()) return
    }

    override fun onResume() {
        super.onResume()
        navigator.setContext(this)
    }

    @CallSuper
    override fun onInitSuccess() {
        setContentView(layoutId)
        onActivityCreated(savedInstanceState)
    }

    override fun onInitError(error: String) {
        setContentView(layoutId)
        onActivityCreated(savedInstanceState)
    }

    private fun validateAuth(): Boolean {
        if (isCheckedAuth()) {
            if (!isLoggedIn()) {
                onRequireLogin()
                return false
            } else {
                presenter.checkUserInfo()
            }
        } else {
            setContentView(layoutId)
            onActivityCreated(savedInstanceState)
        }
        return true
    }

    private fun isLoggedIn(): Boolean = presenter.isLoggedIn()

    private fun onRequireLogin() {
        presenter.requireLogin { presenter.gotoLogin() }
    }

    protected open fun isCheckedAuth(): Boolean = true
}