package com.github.alexxxdev.gitcat.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationRequest(
    val note: String,
    val scopes: List<String>,
    @SerialName("client_secret") val clientSecret: String = "34059348f8fbd428c0a54fb51a7796bf322242c8",
    @SerialName("client_id") val clientId: String = "34e4d51509317cb9de85"
)
