package com.github.alexxxdev.gitcat.ui.main.profile

import android.os.Bundle
import android.view.View
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ui.base.BaseFragment

class ProfileFragment : BaseFragment<ProfileContract.View, ProfilePresenter>(), ProfileContract.View {
    override val layoutId: Int = R.layout.fragment_profile
    override fun providePresenter() = ProfilePresenter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
    }
}