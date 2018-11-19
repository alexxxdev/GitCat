package com.github.alexxxdev.gitcat.data.model.graphql

import kotlinx.serialization.Serializable

@Serializable
data class IssueSmall(
    val title: String,
    val repository: RepositorySmall
)