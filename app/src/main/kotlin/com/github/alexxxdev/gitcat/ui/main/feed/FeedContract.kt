package com.github.alexxxdev.gitcat.ui.main.feed

import androidx.paging.PagedList
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.ui.base.BaseContract

interface FeedContract : BaseContract {
    interface View : BaseContract.View {
        fun setData(user: User)
        fun setFeed(list: PagedList<Feed>)
        fun setState(state: State)
        fun onError(message: String)
        fun setNotifications(notificationPagedList: PagedList<Feed>)
    }

    interface Presenter : BaseContract.Presenter {
        fun onSelectOrganisation(org: OrganizationSmall)
        fun onSelectUser(user: User)
        fun onSelectUserNotifications(user: User)
    }
}