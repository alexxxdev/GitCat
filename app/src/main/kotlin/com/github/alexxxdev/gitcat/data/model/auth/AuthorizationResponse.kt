package com.github.alexxxdev.gitcat.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(val id: Long, val token: String, @SerialName("hashed_token") val hashedToken: String)
