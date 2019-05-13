package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import kotlinx.android.synthetic.main.item_event.view.actionTV
import kotlinx.android.synthetic.main.item_event.view.avatarView
import kotlinx.android.synthetic.main.item_event.view.dateTV
import kotlinx.android.synthetic.main.item_event.view.nameTV
import kotlinx.android.synthetic.main.item_event.view.optionTV
import kotlinx.android.synthetic.main.item_notification.view.typeIV

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var event: Event? = null

    fun bind(item: Feed?) {
        this.event = item as? Event
        this.event?.apply {
            itemView.actionTV.text = type
            itemView.dateTV.text = createdAt
            itemView.nameTV.text = actor?.login
            itemView.optionTV.text = repo?.name
            actor?.avatarUrl?.let { url ->
                GlideApp.with(itemView.context)
                        .load("$url?s=48")
                        .apply(defaultOptions)
                        .into(itemView.avatarView)
            }
            itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.colorAccent))
            when (type) {
                "WatchEvent" -> {
                    itemView.actionTV.text = "starred"
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.indigoA700))
                }
                "DeleteEvent" -> {
                    itemView.actionTV.text = "deleted ${payload?.ref_type} ${payload?.ref} at"
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.redA700))
                }
                "PushEvent" -> {
                    if (payload?.ref?.startsWith("refs/heads/") == true) {
                        itemView.actionTV.text = "pushed to ${payload?.ref?.substring(11)} at"
                    } else {
                        itemView.actionTV.text = "pushed to ${payload?.ref} at"
                    }
                }
                "PullRequestEvent" -> itemView.actionTV.text = "${payload?.action} pull request #${payload?.number}"
                "CreateEvent" -> {
                    itemView.actionTV.text = "created ${payload?.ref_type} ${payload?.ref} at"
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.lightGreenA700))
                }
                "IssueCommentEvent" -> itemView.actionTV.text = "commented on issue #${payload?.issue?.number}"
                "IssuesEvent" -> itemView.actionTV.text = "${payload?.action} issue #${payload?.issue?.number}"
                "ForkEvent" -> {
                    itemView.actionTV.text = "forked"
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.indigoA700))
                }
                "MemberEvent" -> {
                    itemView.actionTV.text = "${payload?.action} ${payload?.member?.login} to"
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.lightGreenA700))
                }
                "CommitCommentEvent" -> itemView.actionTV.text = "commented on commit"
                else -> {
                    itemView.actionTV.text = type
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder {
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
            return EventViewHolder(layout)
        }
    }
}