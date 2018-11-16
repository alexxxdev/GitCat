package com.github.alexxxdev.gitcat.ui.main.feed

import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class FeedPresenter : BasePresenter<FeedContract.View>(), FeedContract.Presenter {

    override fun attachView(view: FeedContract.View) {
        super.attachView(view)
        authRepository.user?.let { user ->
            deliverToView { setData(user) }
        }
    }
}