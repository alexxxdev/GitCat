package com.github.alexxxdev.gitcat.data.model.graphql

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationSmall(
    val id: String,
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val email: String?,
    val location: String?,
    val url: String
)