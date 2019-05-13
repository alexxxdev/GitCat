package com.github.alexxxdev.gitcat.data

import androidx.paging.PageKeyedDataSource
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NotificationsDataSource(val stateListener: (State) -> Unit) : PageKeyedDataSource<Int, Feed>(), KoinComponent {

    val userRepository by inject<UserRepository>()
    val authRepository by inject<AuthRepository>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Feed>) {
        authRepository.user?.let {
            GlobalScope.launch(Dispatchers.Default) {
                userRepository.getUserNotifications(1, true).fold({ list ->
                    stateListener(State.DONE)
                    callback.onResult(list.groupBy { it.unread }.flatMap { it.value }, null, 2)
                }, {
                    stateListener(State.ERROR)
                })
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) {
        /*stateListener(State.LOADING)
        authRepository.user?.let {
            GlobalScope.launch(Dispatchers.Default) {
                userRepository.getUserNotifications(params.key, true).fold({ list ->
                    stateListener(State.DONE)
                    callback.onResult(list, if (params.key < PAGE_COUNT) params.key + 1 else null)
                }, {
                    stateListener(State.ERROR)
                })
            }
        }*/
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) = Unit
}