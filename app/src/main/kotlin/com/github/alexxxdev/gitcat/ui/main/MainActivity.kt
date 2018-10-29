package com.github.alexxxdev.gitcat.ui.main

import android.os.Bundle
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ui.base.BaseActivity

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override val layoutId: Int = R.layout.activity_main
    override fun providePresenter() = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}