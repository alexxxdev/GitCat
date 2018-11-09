package com.github.alexxxdev.gitcat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSmall(
    val login: String,
    val name: String?,
    val avatarUrl: String
)