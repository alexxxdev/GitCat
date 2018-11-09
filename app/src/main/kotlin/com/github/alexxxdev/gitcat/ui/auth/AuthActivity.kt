package com.github.alexxxdev.gitcat.ui.auth

import android.os.Bundle
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ui.auth.login.LoginFragment
import com.github.alexxxdev.gitcat.ui.base.BaseActivity

class AuthActivity : BaseActivity<AuthContract.View, AuthPresenter>(), AuthContract.View {
    override val layoutId: Int = R.layout.activity_auth
    override fun providePresenter() = AuthPresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.instance())
                    .commit()
        }
    }

    override fun isCheckedAuth() = false
}