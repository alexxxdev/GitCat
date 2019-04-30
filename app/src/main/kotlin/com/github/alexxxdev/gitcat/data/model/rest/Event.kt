package com.github.alexxxdev.gitcat.data.model.rest

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val PAGE_SIZE = 30
const val PAGE_COUNT = 10

@Serializable
data class Event(
    val id: String,
    val type: String,
    val public: Boolean,
    @SerialName("created_at") val createdAt: String,
    @Optional val repo: Repo? = null,
    @Optional val actor: Actor? = null,
    @Optional val org: Org? = null,
    @Optional val payload: Payload? = null
) {
    class ItemDiffer : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}

@Serializable
data class Repo(
    val id: Int,
    val name: String,
    val url: String
)

@Serializable
data class Actor(
    val id: Int,
    val login: String,
    @SerialName("display_login") val displayLogin: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val url: String
)

@Serializable
data class Org(
    val id: Int,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val url: String
)

@Serializable
data class Payload(
        @Optional var action: String = "",
        @Optional var ref: String? = "",
        @Optional var ref_type: String = "",
        @Optional var pusher_type: String = "",
        @Optional var push_id: Long = 0L,
        @Optional var size: Int = 0,
        @Optional var distinct_size: Int = 0,
        @Optional var number: Int = 0,
        @Optional var issue: Issue? = null,
        @Optional var member: Member? = null
)

@Serializable
data class Member(
        @Optional var login: String = ""
)


@Serializable
data class Issue(
        @Optional var number: Int = 0
)
