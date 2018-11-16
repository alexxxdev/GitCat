package com.github.alexxxdev.gitcat.ui.main.feed

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.common.GlideApp
import com.github.alexxxdev.gitcat.common.defaultOptions
import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.ext.findBehavior
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed.avatar1
import kotlinx.android.synthetic.main.fragment_feed.avatar2
import kotlinx.android.synthetic.main.fragment_feed.avatar3
import kotlinx.android.synthetic.main.fragment_feed.avatar4
import kotlinx.android.synthetic.main.fragment_feed.avatar5
import kotlinx.android.synthetic.main.fragment_feed.avatar6
import kotlinx.android.synthetic.main.fragment_feed.conm
import kotlinx.android.synthetic.main.fragment_feed.foregroundContainer
import kotlinx.android.synthetic.main.fragment_feed.name1
import kotlinx.android.synthetic.main.fragment_feed.name2
import kotlinx.android.synthetic.main.fragment_feed.name3
import kotlinx.android.synthetic.main.fragment_feed.name4
import kotlinx.android.synthetic.main.fragment_feed.name5
import kotlinx.android.synthetic.main.fragment_feed.name6
import kotlinx.android.synthetic.main.fragment_feed.recyclerView
import kotlinx.android.synthetic.main.fragment_feed.recyclerViewPage
import kotlinx.android.synthetic.main.fragment_feed.toolbar
import kotlinx.android.synthetic.main.fragment_feed.toolbar_avatar
import kotlinx.android.synthetic.main.fragment_feed.toolbar_title
import kotlinx.android.synthetic.main.fragment_profile.avatarImage
import me.saket.inboxrecyclerview.dimming.TintPainter
import org.jetbrains.anko.sdk27.coroutines.onClick
import ru.semper_viventem.backdrop.BackdropBehavior

class FeedFragment : BaseFragment<FeedContract.View, FeedPresenter>(), FeedContract.View {
    override val layoutId: Int = R.layout.fragment_feed
    override fun providePresenter() = FeedPresenter()

    private lateinit var backdropBehavior: BackdropBehavior

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        backdropBehavior = foregroundContainer.findBehavior()

        backdropBehavior.apply {
            attachBackContainer(R.id.backContainer)
            attachToolbar(R.id.toolbar)
            setClosedIcon(R.drawable.ic_drop_open)
            setOpenedIcon(R.drawable.ic_drop_close)
            // add listener
            addOnDropListener(object : BackdropBehavior.OnDropListener {
                override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
                    // TODO: handle listener
                }
            })
        }
        toolbar.setTitle(R.string.app_name)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setExpandablePage(recyclerViewPage)
            tintPainter = TintPainter.uncoveredArea(color = Color.WHITE, opacity = 0.65F)
        }
    }

    override fun setData(user: User) {
        context?.let { context ->
            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .apply(defaultOptions)
                    .into(toolbar_avatar)

            GlideApp.with(context)
                    .load(user.avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar1)

            GlideApp.with(context)
                    .load(user.organizations.nodes[0].avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar2)

            GlideApp.with(context)
                    .load(user.organizations.nodes[1].avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar3)

            GlideApp.with(context)
                    .load(user.organizations.nodes[2].avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar4)

            GlideApp.with(context)
                    .load(user.organizations.nodes[3].avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar5)

            GlideApp.with(context)
                    .load(user.organizations.nodes[4].avatarUrl)
                    .apply(defaultOptions)
                    .into(avatar6)
        }

        toolbar_title.text = user.name
        name1.text = user.name
        name2.text = user.organizations.nodes[0].name
        name3.text = user.organizations.nodes[1].name
        name4.text = user.organizations.nodes[2].name
        name5.text = user.organizations.nodes[3].name
        name6.text = user.organizations.nodes[4].name

        conm.setOnClickListener {
            toolbar_title.text = user.organizations.nodes[0].name
            context?.let { context -> GlideApp.with(context)
                    .load(user.organizations.nodes[0].avatarUrl)
                    .apply(defaultOptions)
                    .into(toolbar_avatar) }
            backdropBehavior.close(true)
        }
    }
}