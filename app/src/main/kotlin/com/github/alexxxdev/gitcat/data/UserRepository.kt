package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.rest.Event

class UserRepository(
    private val authRepository: AuthRepository,
    private val client: GithubRestClient
) {

    init {
        client.token = authRepository.getToken()
    }

    suspend fun getUserEvent(username: String): Result<List<Event>> {
        return client.getUserEvent(username)
    }
}