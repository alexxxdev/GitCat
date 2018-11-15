package com.github.alexxxdev.gitcat.ui.main.profile

import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class ProfilePresenter : BasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

    override fun attachView(view: ProfileContract.View) {
        super.attachView(view)
        authRepository.user?.let { user ->
            deliverToView { setData(user) }
        }
    }
}