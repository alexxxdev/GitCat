package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.common.FooterViewHolder
import com.github.alexxxdev.gitcat.common.widget.recyclerview.BaseAdapter
import com.github.alexxxdev.gitcat.common.widget.recyclerview.DATA_VIEW_TYPE
import com.github.alexxxdev.gitcat.common.widget.recyclerview.FOOTER_VIEW_TYPE
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.Feed
import com.github.alexxxdev.gitcat.data.model.rest.ItemDiffer
import com.github.alexxxdev.gitcat.data.model.rest.Notification

const val EVENT_VIEW_TYPE = DATA_VIEW_TYPE + 10
const val NOTIFICATION_VIEW_TYPE = DATA_VIEW_TYPE + 11

class FeedAdapter : BaseAdapter<Feed, RecyclerView.ViewHolder>(ItemDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EVENT_VIEW_TYPE -> EventViewHolder.create(parent)
            NOTIFICATION_VIEW_TYPE -> NotificationViewHolder.create(parent)
            else -> FooterViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            EVENT_VIEW_TYPE -> (holder as EventViewHolder).bind(getItem(position))
            NOTIFICATION_VIEW_TYPE -> (holder as NotificationViewHolder).bind(getItem(position))
            else -> (holder as FooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (super.getItemViewType(position)) {
            DATA_VIEW_TYPE -> when (getItem(position)) {
                is Event -> EVENT_VIEW_TYPE
                is Notification -> NOTIFICATION_VIEW_TYPE
                else -> FOOTER_VIEW_TYPE
            }
            else -> FOOTER_VIEW_TYPE
        }
    }
}