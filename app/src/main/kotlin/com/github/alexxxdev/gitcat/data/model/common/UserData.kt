package com.github.alexxxdev.gitcat.data.model.common

import com.github.alexxxdev.gitcat.data.model.graphql.User
import kotlinx.serialization.Serializable

@Serializable
data class UserData(val user: User?)