package com.github.alexxxdev.gitcat.ui.main.feed

import android.os.Bundle
import android.view.View
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ui.base.BaseFragment

class FeedFragment : BaseFragment<FeedContract.View, FeedPresenter>(), FeedContract.View {
    override val layoutId: Int = R.layout.fragment_feed
    override fun providePresenter() = FeedPresenter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
    }
}