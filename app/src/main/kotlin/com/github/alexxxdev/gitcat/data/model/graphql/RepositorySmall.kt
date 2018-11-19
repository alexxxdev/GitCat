package com.github.alexxxdev.gitcat.data.model.graphql

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class RepositorySmall(
    val id: String,
    val name: String,
    val description: String?,
    val nameWithOwner: String,
    val url: String,
    val createdAt: String,
    @Optional val parent: RepositorySmall? = null
)