package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.data.model.rest.Notification
import kotlinx.android.synthetic.main.item_notification.view.avatarView
import kotlinx.android.synthetic.main.item_notification.view.dateTV
import kotlinx.android.synthetic.main.item_notification.view.nameTV
import kotlinx.android.synthetic.main.item_notification.view.optionTV
import kotlinx.android.synthetic.main.item_notification.view.typeIV

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var event: Notification? = null

    fun bind(item: Feed?) {
        this.event = item as? Notification
        this.event?.apply {
            itemView.dateTV.text = "$updated_at ${if (unread) "unread" else ""}"
            itemView.nameTV.text = subject.title
            itemView.optionTV.text = repository.name
            repository.owner?.avatar_url?.let { url ->
                GlideApp.with(itemView.context)
                        .load("$url?s=48")
                        .apply(defaultOptions)
                        .into(itemView.avatarView)
            }
            when (this.subject.type) {
                "Issue" -> {
                    itemView.typeIV.setImageResource(R.drawable.ic_open_issue)
                    itemView.typeIV.imageTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.white))
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.lightGreenA700))
                }
                "PullRequest" -> {
                    itemView.typeIV.setImageResource(R.drawable.ic_open_pull_request)
                    itemView.typeIV.imageTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.white))
                    itemView.typeIV.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(R.color.indigoA700))
                }
                else -> itemView.typeIV.setImageResource(0)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder {
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
            return NotificationViewHolder(layout)
        }
    }
}