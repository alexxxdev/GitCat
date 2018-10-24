package com.github.alexxxdev.gitcat.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import net.grandcentrix.thirtyinch.TiFragment

abstract class BaseFragment<V : BaseContract.View, P : BasePresenter<V>> : TiFragment<P, V>(), BaseContract.View {
    abstract val layoutId: Int

    protected abstract fun onFragmentCreated(view: View, savedInstanceState: Bundle?)

    @SuppressLint("MissingSuperCall")
    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(layoutId, container, false)
        return fragmentView
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated(view, savedInstanceState)
    }
}