package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.fuelcomfy.annotation.Body
import com.github.alexxxdev.fuelcomfy.annotation.FuelInterface
import com.github.alexxxdev.fuelcomfy.annotation.Header
import com.github.alexxxdev.fuelcomfy.annotation.Param
import com.github.alexxxdev.fuelcomfy.annotation.Post
import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.UserData
import com.github.kittinunf.result.Result

@FuelInterface
interface GithubGraphQLService {
    @Post("/graphql")
    @Header("Authorization", "bearer {token}")
    suspend fun getUserInfo(@Param("token") token: String, @Body query: String): Result<GraphQLData<UserData>, Exception>
}