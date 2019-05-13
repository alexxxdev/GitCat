package com.github.alexxxdev.gitcat.data.model.rest

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String,
    val unread: Boolean,
    val updated_at: String,
    val last_read_at: String?,
    val url: String?,
    val subscription_url: String?,
    val subject: Subject,
    val repository: NotificationRepo
) : Feed {
    override fun areContentsTheSameImpl(oldItem: Feed, newItem: Feed): Boolean {
        return (oldItem as Notification).id == (newItem as Notification).id
    }

    override fun areItemsTheSameImpl(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}

@Serializable
data class Subject(
    val title: String?,
    val type: String?,
    val url: String?,
    val latest_comment_url: String?
)

@Serializable
data class NotificationRepo(
    val id: String,
    val node_id: String?,
    val name: String,
    val full_name: String,
    val private: Boolean?,
    val html_url: String?,
    val description: String?,
    val fork: Boolean?,
    val url: String?,
    val forks_url: String?,
    val owner: Owner?
)

@Serializable
data class Owner(
    val login: String?,
    val id: String,
    val node_id: String?,
    val avatar_url: String?,
    val url: String?,
    val type: String?
)