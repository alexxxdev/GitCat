package com.github.alexxxdev.gitcat.data.model.graphql

import kotlinx.serialization.Serializable

@Serializable
data class UserSmall(
    val login: String,
    val name: String?,
    val avatarUrl: String
)