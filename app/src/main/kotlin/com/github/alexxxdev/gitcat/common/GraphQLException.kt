package com.github.alexxxdev.gitcat.common

import com.github.alexxxdev.gitcat.data.model.common.GraphQLError

class GraphQLException(val errors: List<GraphQLError>?) : Exception(errors?.get(0)?.message
        ?: "Unknown error")