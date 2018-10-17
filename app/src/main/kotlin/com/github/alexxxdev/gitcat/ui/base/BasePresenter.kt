package com.github.alexxxdev.gitcat.ui.base

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

open class BasePresenter<V : BaseContract.View> : MvpBasePresenter<V>(), BaseContract.Presenter<V>
