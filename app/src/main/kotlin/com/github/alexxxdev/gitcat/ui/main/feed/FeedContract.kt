package com.github.alexxxdev.gitcat.ui.main.feed

import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.ui.base.BaseContract

interface FeedContract : BaseContract {
    interface View : BaseContract.View {
        fun setData(user:User)
    }
    interface Presenter : BaseContract.Presenter
}