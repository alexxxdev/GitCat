package com.github.alexxxdev.gitcat.common.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ExtendedRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            showEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            showEmptyView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            showEmptyView()
        }
    }

    var emptyView: View? = null
        set(value) {
            field = value
            showEmptyView()
        }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (isInEditMode) return
        adapter?.apply {
            registerAdapterDataObserver(observer)
            observer.onChanged()
        }
    }

    private fun showEmptyView() {
        if (adapter != null) {
            emptyView?.let {
                if (adapter?.itemCount == 0) {
                    showEmptyOrSelf(false)
                } else {
                    showEmptyOrSelf(true)
                }
            }
        } else {
            emptyView?.let { showEmptyOrSelf(false) }
        }
    }

    private fun showEmptyOrSelf(showRecyclerView: Boolean) {
        visibility = if (showRecyclerView) VISIBLE else GONE
        emptyView?.visibility = if (!showRecyclerView) VISIBLE else GONE
    }
}