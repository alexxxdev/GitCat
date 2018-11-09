package com.github.alexxxdev.gitcat.data

import android.util.Log
import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.UserData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import com.github.kittinunf.result.Result
import kotlinx.serialization.json.JSON

class GithubGraphQLClient {
    var token: String? = null

    fun getUserInfo(query: String): Triple<Request, Response, Result<GraphQLData<UserData>, FuelError>> {
        val body = Fuel.post("https://api.github.com/graphql")
                .header("Authorization" to "bearer $token")
                .body(query)
                .responseObject(kotlinxDeserializerOf<GraphQLData<UserData>>(GraphQLData.serializer(UserData.serializer()), json = JSON.nonstrict))
        Log.v("FuelLogger", "success ${body.first.httpString()}\n${body.first.path} ${body.third.component1()?.data?.toString()}")
        Log.v("FuelLogger", "error ${body.first.httpString()}\n${body.first.path} ${String(body.third.component2()?.errorData ?: ByteArray(1))}")
        return body
    }
}