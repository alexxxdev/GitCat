package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.fuelcomfy.KotlinSerializationAdapter
import com.github.alexxxdev.fuelcomfy.annotation.FuelInterface
import com.github.alexxxdev.fuelcomfy.annotation.Get
import com.github.alexxxdev.fuelcomfy.annotation.Header
import com.github.alexxxdev.fuelcomfy.annotation.Param
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.Notification
import com.github.kittinunf.result.Result

@FuelInterface(KotlinSerializationAdapter::class)
interface GithubService {

    @Get("/users/{username}/received_events?page={page}")
    @Header("Authorization", "bearer {token}")
    suspend fun getUserEvent(@Param("username") username: String, @Param("page") page: Int, @Param("token") token: String): Result<List<Event>, Exception>

    @Get("/notifications?page={page}")
    @Header("Authorization", "bearer {token}")
    suspend fun getUserNotification(@Param("page") page: Int, @Param("token") token: String): Result<List<Notification>, Exception>
}