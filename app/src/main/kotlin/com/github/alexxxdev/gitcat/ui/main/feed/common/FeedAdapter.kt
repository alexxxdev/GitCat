package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.rest.Event
import xyz.schwaab.avvylib.AvatarView

class FeedAdapter : ListAdapter<Event, EventViewHolder>(Event.ItemDiffer()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(layout)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.event = getItem(position)
        holder.bind()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var event: Event

    private val avatar = itemView.findViewById<AvatarView>(R.id.avatarView)
    private val name = itemView.findViewById<TextView>(R.id.nameTV)
    private val action = itemView.findViewById<TextView>(R.id.actionTV)
    private val option = itemView.findViewById<TextView>(R.id.optionTV)
    private val date = itemView.findViewById<TextView>(R.id.dateTV)

    fun bind() {
        action.text = event.type
        date.text = event.createdAt
        name.text = event.actor?.login
        option.text = event.repo?.name

        GlideApp.with(itemView.context)
                .load(event.actor?.avatarUrl)
                .apply(defaultOptions)
                .into(avatar)
    }
}