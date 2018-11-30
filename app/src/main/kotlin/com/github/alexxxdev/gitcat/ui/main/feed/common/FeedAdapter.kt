package com.github.alexxxdev.gitcat.ui.main.feed.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.common.FooterViewHolder
import com.github.alexxxdev.gitcat.common.widget.recyclerview.BaseAdapter
import com.github.alexxxdev.gitcat.common.widget.recyclerview.DATA_VIEW_TYPE
import com.github.alexxxdev.gitcat.data.model.rest.Event

class FeedAdapter : BaseAdapter<Event, RecyclerView.ViewHolder>(Event.ItemDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            EventViewHolder.create(parent)
        } else {
            FooterViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as EventViewHolder).bind(getItem(position))
        } else {
            (holder as FooterViewHolder).bind(state)
        }
    }
}