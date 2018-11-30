package com.github.alexxxdev.gitcat.data

import android.util.Log
import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

class GithubRestClient {
    var token: String? = null

    init {
        FuelManager.instance.basePath = "https://api.github.com"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun getUserEvent(username: String, page: Int): Result<List<Event>> {
        val responseObject = Fuel.get("/users/$username/received_events?page=$page")
                .header("Authorization" to "bearer $token")
                .responseObject(kotlinxDeserializerOf<List<Event>>(Event.serializer().list, json = JSON.nonstrict))

        val let = responseObject.third.component1()?.let { JSON.indented.stringify<List<Event>>(Event.serializer().list, it) }

        Log.v("fuel", "--> ${responseObject.first.httpString()}\n\n<-- ${responseObject.second.statusCode} ${responseObject.second.url}\n${responseObject.second.responseMessage}\n $let")

        return if (responseObject.second.statusCode == CODE_SUCCESS) {
            Result.of(responseObject.third.component1())
        } else {
            Result.error(Error.of(responseObject.second.statusCode, responseObject.third.component2()))
        }
    }
}