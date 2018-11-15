package com.github.alexxxdev.gitcat.ui.main.profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import com.github.alexxxdev.gitcat.R.id.image
import androidx.core.view.ViewCompat.setScaleY
import androidx.core.view.ViewCompat.setScaleX
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.github.alexxxdev.gitcat.common.*
import com.github.alexxxdev.gitcat.data.model.User
import com.google.android.material.appbar.AppBarLayout


class ProfileFragment : BaseFragment<ProfileContract.View, ProfilePresenter>(), ProfileContract.View {
    override val layoutId: Int = R.layout.fragment_profile
    override fun providePresenter() = ProfilePresenter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        avatarImage.isAnimating = true

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val minHeight = ViewCompat.getMinimumHeight(collapsingLayout) * 2.5f
            val scale = (minHeight + verticalOffset) / minHeight
            avatarImage.scaleX = if (scale >= 0) scale else 0f
            avatarImage.scaleY = if (scale >= 0) scale else 0f
        })
    }

    override fun setData(user: User) {
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

        context?.let { context->
            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .apply(defaultOptions)
                    .into(avatarImage)

            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .transform(
                            MultiTransformation(
                                    BlurTransformation(context),
                                    ColorFilterTransformation(context.getColor(R.color.avatar_filter))
                            ))
                    //.apply(defaultOptions)
                    .into(backgroundImage)
        }
    }
}