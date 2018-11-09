package com.github.alexxxdev.gitcat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IssueSmall(
    val title: String,
    val repository: RepositorySmall
)