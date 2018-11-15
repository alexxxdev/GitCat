package com.github.alexxxdev.gitcat.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.bumptech.glide.load.MultiTransformation
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.BlurTransformation
import com.github.alexxxdev.gitcat.common.ColorFilterTransformation
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_profile.appbarLayout
import kotlinx.android.synthetic.main.fragment_profile.avatarImage
import kotlinx.android.synthetic.main.fragment_profile.backgroundImage
import kotlinx.android.synthetic.main.fragment_profile.bioTextView
import kotlinx.android.synthetic.main.fragment_profile.collapsingLayout
import kotlinx.android.synthetic.main.fragment_profile.companyTextView
import kotlinx.android.synthetic.main.fragment_profile.emailTextView
import kotlinx.android.synthetic.main.fragment_profile.followersTextView
import kotlinx.android.synthetic.main.fragment_profile.followingTextView
import kotlinx.android.synthetic.main.fragment_profile.locationTextView
import kotlinx.android.synthetic.main.fragment_profile.loginTextView
import kotlinx.android.synthetic.main.fragment_profile.nameTextView
import kotlinx.android.synthetic.main.fragment_profile.repositoriesTextView
import kotlinx.android.synthetic.main.fragment_profile.starsTextView
import kotlinx.android.synthetic.main.fragment_profile.websiteTextView

class ProfileFragment : BaseFragment<ProfileContract.View, ProfilePresenter>(), ProfileContract.View {
    override val layoutId: Int = R.layout.fragment_profile
    override fun providePresenter() = ProfilePresenter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val minHeight = ViewCompat.getMinimumHeight(collapsingLayout) * 2.4f
            val scale = (minHeight + verticalOffset) / minHeight
            avatarImage.scaleX = if (scale >= 0) scale else 0f
            avatarImage.scaleY = if (scale >= 0) scale else 0f
        })
    }

    override fun setData(user: User) {
        context?.let { context ->
            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .apply(defaultOptions)
                    .into(avatarImage)

            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .apply(defaultOptions)
                    .into(backgroundImage)

            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .transform(
                            MultiTransformation(
                                    BlurTransformation(context),
                                    ColorFilterTransformation(context.resources.getColor(R.color.avatarFilter))
                            ))
                    .apply(defaultOptions)
                    .into(backgroundImage)
        }

        nameTextView.text = user.name
        loginTextView.text = user.login
        bioTextView.text = user.bio
        repositoriesTextView.text = user.repositories.totalCount.toString()
        starsTextView.text = user.starredRepositories.totalCount.toString()
        followersTextView.text = user.followers.totalCount.toString()
        followingTextView.text = user.following.totalCount.toString()
        emailTextView.text = user.email
        locationTextView.text = user.location
        websiteTextView.text = user.websiteUrl
        companyTextView.text = user.company
    }
}