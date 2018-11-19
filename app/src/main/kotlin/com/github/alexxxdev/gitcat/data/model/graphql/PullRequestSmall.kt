package com.github.alexxxdev.gitcat.data.model.graphql

import kotlinx.serialization.Serializable

@Serializable
data class PullRequestSmall(
    val title: String,
    val body: String,
    val repository: RepositorySmall
)