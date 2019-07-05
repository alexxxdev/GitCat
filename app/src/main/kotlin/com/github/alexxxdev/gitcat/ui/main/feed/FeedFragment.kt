package com.github.alexxxdev.gitcat.ui.main.feed

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateMargins
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.widget.AvatarWithNameView
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.ext.findBehavior
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import com.github.alexxxdev.gitcat.ui.main.feed.common.FeedAdapter
import kotlinx.android.synthetic.main.fragment_feed.avatarInToolbar
import kotlinx.android.synthetic.main.fragment_feed.avatarViewContainer
import kotlinx.android.synthetic.main.fragment_feed.feedEmptyView
import kotlinx.android.synthetic.main.fragment_feed.foregroundContainer
import kotlinx.android.synthetic.main.fragment_feed.recyclerView
import kotlinx.android.synthetic.main.fragment_feed.toolbar
import kotlinx.android.synthetic.main.fragment_feed.userAvatarView
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.support.v4.toast
import ru.semper_viventem.backdrop.BackdropBehavior

class FeedFragment : BaseFragment<FeedContract.View, FeedPresenter>(), FeedContract.View {
    override val layoutId: Int = R.layout.fragment_feed
    override fun providePresenter() = FeedPresenter()

    private lateinit var backdropBehavior: BackdropBehavior

    private val feedAdapter = FeedAdapter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        backdropBehavior = foregroundContainer.findBehavior()

        backdropBehavior.apply {
            attachBackContainer(R.id.backScrollContainer)
            attachToolbar(R.id.toolbar)
            setClosedIcon(R.drawable.ic_drop_open)
            setOpenedIcon(R.drawable.ic_drop_close)
            // add listener
            addOnDropListener(object : BackdropBehavior.OnDropListener {
                override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
                }
            })
        }
        toolbar.setTitle(R.string.app_name)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            emptyView = feedEmptyView
            adapter = feedAdapter
        }
    }

    override fun setData(user: User) {
        context?.let { context ->
            avatarInToolbar.name = user.name
            avatarInToolbar.avatarUrl = user.avatarUrl

            userAvatarView.name = user.name
            userAvatarView.avatarUrl = user.avatarUrl
            userAvatarView.setOnClickListener { selectUser(user) }
            userAvatarView.setOnNotifClickListener { selectUserNotifications(user) }

            avatarViewContainer.removeViews(1, avatarViewContainer.childCount-1)
            user.organizations.nodes.sortedBy { it.name }.forEach { org ->
                val avatarWithNameView = AvatarWithNameView(context)
                avatarWithNameView.name = org.name
                avatarWithNameView.avatarUrl = org.avatarUrl
                avatarWithNameView.backgroundResource = R.drawable.background
                avatarViewContainer.addView(avatarWithNameView)
                (avatarWithNameView.layoutParams as ViewGroup.MarginLayoutParams).updateMargins(top = context.resources.getDimensionPixelOffset(R.dimen.avatar_with_name_top_margin))
                avatarWithNameView.setOnClickListener { selectOrganisation(org) }
            }
        }
    }

    override fun setFeed(list: PagedList<Feed>) {
        feedAdapter.submitList(list)
    }

    override fun setNotifications(notificationPagedList: PagedList<Feed>) {
        feedAdapter.submitList(notificationPagedList)
    }

    override fun setState(state: State) {
        feedAdapter.state = state
    }

    override fun onError(message: String) {
        toast(message)
    }

    private fun selectOrganisation(org: OrganizationSmall) {
        avatarInToolbar.name = org.name
        avatarInToolbar.avatarUrl = org.avatarUrl
        backdropBehavior.close(true)
        presenter.onSelectOrganisation(org)
    }

    private fun selectUser(user: User) {
        avatarInToolbar.name = user.name
        avatarInToolbar.avatarUrl = user.avatarUrl
        backdropBehavior.close(true)
        presenter.onSelectUser(user)
    }

    private fun selectUserNotifications(user: User) {
        avatarInToolbar.name = user.name
        avatarInToolbar.avatarUrl = user.avatarUrl
        backdropBehavior.close(true)
        presenter.onSelectUserNotifications(user)
    }
}