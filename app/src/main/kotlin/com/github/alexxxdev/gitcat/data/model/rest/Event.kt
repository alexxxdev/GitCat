package com.github.alexxxdev.gitcat.data.model.rest

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    @SerialName("gravatar_id") val gravatarId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val url: String
)

@Serializable
data class Org(
    val id: Int,
    val login: String,
    @SerialName("gravatar_id") val gravatarId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val url: String
)

@Serializable
data class Payload(
    @Optional var action: String = ""
)
