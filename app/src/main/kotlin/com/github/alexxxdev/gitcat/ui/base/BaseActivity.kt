package com.github.alexxxdev.gitcat.ui.base

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>, VS : BaseViewState<V>> : MvpViewStateActivity<V, P, VS>(), BaseContract.View {
    abstract val layoutId: Int
    abstract override fun createViewState(): VS
    abstract override fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}