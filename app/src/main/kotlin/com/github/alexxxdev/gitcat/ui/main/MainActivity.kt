package com.github.alexxxdev.gitcat.ui.main

import android.os.Bundle
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ext.inTransaction
import com.github.alexxxdev.gitcat.ui.base.BaseActivity
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import com.github.alexxxdev.gitcat.ui.main.feed.FeedFragment
import com.github.alexxxdev.gitcat.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override val layoutId: Int = R.layout.activity_main
    override fun providePresenter() = MainPresenter()

    private var containers = emptyMap<Int, Pair<ContainerFragment, BaseFragment<*, *>>>()
    private var currentIndex = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        containers += R.id.navigation_home to ( ContainerFragment() to FeedFragment())
        containers += R.id.navigation_profile to ( ContainerFragment() to ProfileFragment())

        if (savedInstanceState == null) {
            navigateToItem(R.id.navigation_home)
        }

        navigationView.setOnNavigationItemSelectedListener { menuItem ->
            navigateToItem(menuItem.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onInitSuccess() {
        super<BaseActivity>.onInitSuccess()
    }

    override fun onInitError(error: String) {
        toast(error)
    }

    private fun navigateToItem(id: Int) {
        currentIndex = id

        containers[id]?.let { pair ->
            supportFragmentManager.inTransaction {
                // setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                if (pair.first.isAdded || pair.first.isDetached) {
                    attach(pair.first)
                } else {
                    add(R.id.container, pair.first)
                }
                containers.filter { it.key != id && it.value.first.isAdded }.forEach { detach(it.value.first) }
            }
            navigationView.postDelayed({
                navigator.setFragmentManager(pair.first.childFragmentManager)
                if (pair.first.childFragmentManager.backStackEntryCount == 0) {
                    navigator.navigateToFragment(pair.second, false)
                }
            }, 50) // TODO crutch
        }
    }

    override fun onBackPressed() {
        containers[currentIndex]?.let { pair ->
            if (pair.first.childFragmentManager.backStackEntryCount>0) {
                pair.first.childFragmentManager.popBackStackImmediate()
                return
            }
        }
        super.onBackPressed()
    }
}