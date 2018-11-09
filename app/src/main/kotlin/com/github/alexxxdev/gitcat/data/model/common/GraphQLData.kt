package com.github.alexxxdev.gitcat.data.model.common

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class GraphQLData<DATA>(
    @Optional val data: DATA? = null,
    @Optional val errors: List<GraphQLError> = emptyList()
)