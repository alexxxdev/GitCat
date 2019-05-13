package com.github.alexxxdev.gitcat.ui.main.feed

import androidx.paging.PagedList
import com.github.alexxxdev.gitcat.common.MainThreadExecutor
import com.github.alexxxdev.gitcat.data.FeedDataSource
import com.github.alexxxdev.gitcat.data.NotificationsDataSource
import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.data.model.rest.PAGE_SIZE
import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import java.util.concurrent.Executors
import net.grandcentrix.thirtyinch.kotlin.deliverToView

class FeedPresenter : BasePresenter<FeedContract.View>(), FeedContract.Presenter {

    private var eventPagedList: PagedList<Feed>
    private lateinit var notificationPagedList: PagedList<Feed>
    private var eventDataSource: FeedDataSource
    private var notificationDataSource: NotificationsDataSource
    private val config: PagedList.Config

    init {
        config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPrefetchDistance(PAGE_SIZE)
                .build()

        eventDataSource = FeedDataSource {
            deliverToView { setState(it) }
        }

        notificationDataSource = NotificationsDataSource {
            deliverToView { setState(it) }
        }

        eventPagedList = PagedList.Builder(eventDataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()
    }

    override fun attachView(view: FeedContract.View) {
        super.attachView(view)

        authRepository.user?.let { user ->
            deliverToView {
                setData(user)
                setFeed(eventPagedList)
            }
        }
    }

    override fun onSelectUser(user: User) {
        eventDataSource = FeedDataSource {
            deliverToView { setState(it) }
        }
        eventPagedList = PagedList.Builder(eventDataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()
        deliverToView { setFeed(eventPagedList) }
    }

    override fun onSelectOrganisation(org: OrganizationSmall) {
    }

    override fun onSelectUserNotifications(user: User) {
        notificationDataSource = NotificationsDataSource {
            deliverToView { setState(it) }
        }
        notificationPagedList = PagedList.Builder(notificationDataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()
        deliverToView { setNotifications(notificationPagedList) }
    }
}