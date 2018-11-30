package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.rest.Event
import kotlinx.android.synthetic.main.item_event.view.actionTV
import kotlinx.android.synthetic.main.item_event.view.avatarView
import kotlinx.android.synthetic.main.item_event.view.dateTV
import kotlinx.android.synthetic.main.item_event.view.nameTV
import kotlinx.android.synthetic.main.item_event.view.optionTV

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var event: Event? = null

    fun bind(item: Event?) {
        this.event = item
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
        }
    }

    companion object {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder {
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
            return EventViewHolder(layout)
        }
    }
}