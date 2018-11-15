package com.github.alexxxdev.gitcat.ui.main.profile

import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.ui.base.BaseContract

interface ProfileContract : BaseContract {
    interface View : BaseContract.View {
        fun setData(user: User)
    }
    interface Presenter : BaseContract.Presenter
}