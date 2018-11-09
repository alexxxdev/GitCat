package com.github.alexxxdev.gitcat.ui.base

import net.grandcentrix.thirtyinch.TiView
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread

interface BaseContract {
    interface View : TiView {
        @CallOnMainThread
        fun onInitSuccess() = Unit

        @CallOnMainThread
        fun onInitError(error: String) = Unit
    }
    interface Presenter
}