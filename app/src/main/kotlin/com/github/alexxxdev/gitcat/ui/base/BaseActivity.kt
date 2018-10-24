package com.github.alexxxdev.gitcat.ui.base

import android.os.Bundle
import net.grandcentrix.thirtyinch.TiActivity

abstract class BaseActivity<V : BaseContract.View, P : BasePresenter<V>> : TiActivity<P, V>(), BaseContract.View {
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}