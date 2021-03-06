package com.github.alexxxdev.gitcat.data

import androidx.paging.PageKeyedDataSource
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.data.model.rest.PAGE_COUNT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedDataSource(val stateListener: (State) -> Unit) : PageKeyedDataSource<Int, Feed>(), KoinComponent {
    private var remove: Boolean = false

    val userRepository by inject<UserRepository>()
    val authRepository by inject<AuthRepository>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Feed>) {
        if (remove) {
            stateListener(State.DONE)
            callback.onResult(emptyList(), null, null)
            return
        }
        authRepository.user?.let { user ->
            GlobalScope.launch(Dispatchers.Default) {
                userRepository.getUserEvent(user.login, 1).fold({ list ->
                    stateListener(State.DONE)
                    callback.onResult(list, null, 2)
                }, {
                    stateListener(State.ERROR)
                })
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) {
        stateListener(State.LOADING)
        authRepository.user?.let { user ->
            GlobalScope.launch(Dispatchers.Default) {
                userRepository.getUserEvent(user.login, params.key).fold({ list ->
                    stateListener(State.DONE)
                    callback.onResult(list, if (params.key < PAGE_COUNT) params.key + 1 else null)
                }, {
                    stateListener(State.ERROR)
                })
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) = Unit

    fun clear() {
        remove = true
        // invalidate()
    }
}