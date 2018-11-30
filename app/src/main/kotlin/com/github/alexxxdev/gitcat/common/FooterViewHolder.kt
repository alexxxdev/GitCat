package com.github.alexxxdev.gitcat.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.widget.recyclerview.State
import kotlinx.android.synthetic.main.item_footer.view.textView

class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(state: State) {
        itemView.textView.text = state.name
    }

    companion object {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder {
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
            return FooterViewHolder(layout)
        }
    }
}