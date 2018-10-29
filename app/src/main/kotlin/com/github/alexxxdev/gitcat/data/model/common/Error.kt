package com.github.alexxxdev.gitcat.data.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(val message: String, @SerialName("documentation_url") val documentationUrl: String)