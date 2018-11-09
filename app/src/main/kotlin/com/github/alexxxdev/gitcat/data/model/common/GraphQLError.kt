package com.github.alexxxdev.gitcat.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class GraphQLError(
    val message: String
)