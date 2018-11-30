package com.github.alexxxdev.gitcat.ui.main.feed

import androidx.paging.PagedList
import com.github.alexxxdev.gitcat.common.MainThreadExecutor
import com.github.alexxxdev.gitcat.data.FeedDataSource
import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.PAGE_SIZE
import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import java.util.concurrent.Executors
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class FeedPresenter : BasePresenter<FeedContract.View>(), FeedContract.Presenter {

    private var pagedList: PagedList<Event>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPrefetchDistance(PAGE_SIZE)
                .build()

        val dataSource = FeedDataSource {
            deliverToView { setState(it) }
        }

        pagedList = PagedList.Builder(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()
    }

    override fun attachView(view: FeedContract.View) {
        super.attachView(view)

        authRepository.user?.let { user ->
            deliverToView {
                setData(user)
                setFeed(pagedList)
            }
        }
    }

    override fun onSelectUser(user: User) {
    }

    override fun onSelectOrganisation(org: OrganizationSmall) {
    }
}