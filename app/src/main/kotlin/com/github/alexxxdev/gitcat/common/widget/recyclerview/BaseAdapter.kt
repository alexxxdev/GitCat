package com.github.alexxxdev.gitcat.common.widget.recyclerview

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

const val DATA_VIEW_TYPE = 1
const val FOOTER_VIEW_TYPE = 2

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, VH>(diffCallback) {
    init {
        setHasStableIds(true)
    }

    var state = State.LOADING
        set(value) {
            field = value
            notifyItemChanged(super.getItemCount())
        }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }
}