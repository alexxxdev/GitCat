package com.github.alexxxdev.gitcat.data.model.rest

import androidx.recyclerview.widget.DiffUtil

interface Feed {
    fun areItemsTheSameImpl(oldItem: Feed, newItem: Feed): Boolean
    fun areContentsTheSameImpl(oldItem: Feed, newItem: Feed): Boolean
}

class ItemDiffer : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.areItemsTheSameImpl(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.areContentsTheSameImpl(oldItem, newItem)
    }
}