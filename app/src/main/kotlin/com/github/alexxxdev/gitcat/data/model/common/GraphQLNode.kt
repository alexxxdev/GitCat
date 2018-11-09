package com.github.alexxxdev.gitcat.data.model.common

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class GraphQLNode<T>(
    val totalCount: Int,
    @Optional val nodes: List<T> = emptyList()
)