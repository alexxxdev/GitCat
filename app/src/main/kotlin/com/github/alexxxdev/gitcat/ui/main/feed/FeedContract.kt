package com.github.alexxxdev.gitcat.ui.main.feed

import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.ui.base.BaseContract

interface FeedContract : BaseContract {
    interface View : BaseContract.View {
        fun setData(user: User)
        fun setFeed(list: List<Event>)
        fun onError(message: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun onSelectOrganisation(org: OrganizationSmall)
        fun onSelectUser(user: User)
    }
}