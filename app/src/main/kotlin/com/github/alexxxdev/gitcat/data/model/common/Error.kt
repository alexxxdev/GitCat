package com.github.alexxxdev.gitcat.data.model.common

import com.github.alexxxdev.gitcat.common.GraphQLException
import com.github.kittinunf.fuel.core.FuelError
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parse

class Error(val exception: Exception = Exception("Unknown error"), val code: Int = 0, private val msg: String? = null) {
    val message: String
        get() {
            return msg ?: exception.message ?: "Unknown error"
        }

    companion object {
        fun of(ex: Exception) = Error(ex)

        fun of(code: Int, ex: Exception) = Error(ex)

        @UseExperimental(ImplicitReflectionSerializer::class)
        fun of(code: Int, ex: FuelError?): Error {
            val exception = ex?.exception ?: Exception("Unknown error")
            ex?.let { error ->
                val string = String(error.errorData)
                val error = JSON.nonstrict.parse<Data>(string)
                return Error(exception as Exception, code, error.message)
            }
            return Error(exception as Exception, code)
        }

        fun of(code: Int, errors: List<GraphQLError>?): Error {
            val exception = GraphQLException(errors)
            return Error(exception, code)
        }
    }

    @Serializable
    data class Data(@SerialName("message") val message: String, @SerialName("documentation_url") val doc: String)
}