package com.github.alexxxdev.gitcat.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import kotlinx.android.synthetic.main.widget_avatar_with_name.view.avatarView
import kotlinx.android.synthetic.main.widget_avatar_with_name.view.nameTextView
import kotlinx.android.synthetic.main.widget_avatar_with_name.view.notifButton
import org.jetbrains.anko.textColor

class AvatarWithNameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.widget_avatar_with_name, this, true)

        val textColor = nameTextView.currentTextColor
        attrs?.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "textAppearance", 0)?.let { value ->
            nameTextView.setTextAppearance(context, value)
            nameTextView.textColor = textColor
        }
    }

    var name: String?
        get() = nameTextView.text.toString()
        set(value) {
            nameTextView.text = value
        }

    var avatarUrl: String? = null
        set(value) {
            value?.let {
                GlideApp.with(context)
                        .load(value)
                        .apply(defaultOptions)
                        .into(avatarView)
            }
        }

    fun setOnNotifClickListener(block: () -> Unit) {
        notifButton.visibility = View.VISIBLE
        notifButton.setOnClickListener { block() }
    }
}