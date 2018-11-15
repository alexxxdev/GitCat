package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.common.UserData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.json.JSON

class GithubGraphQLClient {
    var token: String? = null

    fun getUserInfo(query: String): Result<GraphQLData<UserData>> {
        val responseObject = Fuel.post("https://api.github.com/graphql")
                .header("Authorization" to "bearer $token")
                .body(query)
                .responseObject(kotlinxDeserializerOf<GraphQLData<UserData>>(GraphQLData.serializer(UserData.serializer()), json = JSON.nonstrict))

        return if (responseObject.second.statusCode == CODE_SUCCESS) {
            val response = responseObject.third.component1()
            if (response?.data == null) {
                Result.error(Error.of(0, response?.errors))
            } else {
                Result.of(response)
            }
        } else {
            Result.error(Error.of(responseObject.second.statusCode, responseObject.third.component2()))
        }
    }
}