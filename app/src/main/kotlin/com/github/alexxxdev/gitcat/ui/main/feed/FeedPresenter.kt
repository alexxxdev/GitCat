package com.github.alexxxdev.gitcat.ui.main.feed

import com.github.alexxxdev.gitcat.data.UserRepository
import com.github.alexxxdev.gitcat.data.model.graphql.OrganizationSmall
import com.github.alexxxdev.gitcat.data.model.graphql.User
import com.github.alexxxdev.gitcat.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import org.koin.standalone.inject

class FeedPresenter : BasePresenter<FeedContract.View>(), FeedContract.Presenter {

    val job = Job()

    protected val userRepository by inject<UserRepository>()

    override fun attachView(view: FeedContract.View) {
        super.attachView(view)
        authRepository.user?.let { user ->

            GlobalScope.launch(Dispatchers.Main + job) {
                async(Dispatchers.Default + job) { userRepository.getUserEvent(user.login) }
                        .await()
                        .fold({
                            deliverToView { setFeed(it) }
                        }, { error ->
                            deliverToView { onError(error.message) }
                        })
            }

            deliverToView { setData(user) }
        }
    }

    override fun onSelectUser(user: User) {
    }

    override fun onSelectOrganisation(org: OrganizationSmall) {
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}