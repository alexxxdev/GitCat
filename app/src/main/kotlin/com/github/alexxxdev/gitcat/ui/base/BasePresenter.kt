package com.github.alexxxdev.gitcat.ui.base

import net.grandcentrix.thirtyinch.TiConfiguration
import net.grandcentrix.thirtyinch.TiPresenter
import org.koin.standalone.KoinComponent

val PRESENTER_CONFIG = TiConfiguration.Builder()
        .setRetainPresenterEnabled(true)
        .setCallOnMainThreadInterceptorEnabled(true)
        .setDistinctUntilChangedInterceptorEnabled(true)
        .build()

open class BasePresenter<V : BaseContract.View> : TiPresenter<V>(PRESENTER_CONFIG), BaseContract.Presenter, KoinComponent