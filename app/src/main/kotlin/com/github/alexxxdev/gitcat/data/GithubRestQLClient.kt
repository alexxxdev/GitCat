package com.github.alexxxdev.gitcat.data

import com.github.kittinunf.fuel.core.FuelManager

class GithubRestQLClient {
    private var token: String? = null

    init {
        FuelManager.instance.basePath = "https://api.github.com"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    fun setToken(token: String?) {
        this.token = token
    }
}