package com.github.alexxxdev.gitcat.data

import androidx.paging.PageKeyedDataSource
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.PAGE_COUNT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedDataSource(val stateListener: (State) -> Unit) : PageKeyedDataSource<Int, Event>(), KoinComponent {

    val userRepository by inject<UserRepository>()
    val authRepository by inject<AuthRepository>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Event>) {
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) = Unit
}