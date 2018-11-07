package com.github.alexxxdev.gitcat.ui.base

import androidx.fragment.app.FragmentManager
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ext.hideKeyboard
import com.github.alexxxdev.gitcat.ext.inTransaction
import org.jetbrains.anko.startActivity

open class BaseNavigator {
    private var activity: BaseActivity<*, *>? = null
    private var fragmentManager: FragmentManager? = null

    fun setContext(ctx: BaseActivity<*, *>) {
        activity = ctx
    }

    internal fun navigateToFragment(fragment: BaseFragment<*, *>, addToBackStack: Boolean = true) {
        activity?.let { act ->
            (fragmentManager ?: act.getSupportFragmentManager()).inTransaction {
                if (addToBackStack) addToBackStack(fragment.javaClass.name)
                replace(R.id.container, fragment, fragment.javaClass.name)
            }
        }
    }

    internal fun navigateToFragment(fm: FragmentManager, fragment: BaseFragment<*, *>, addToBackStack: Boolean = true) {
        activity?.let { act ->
            fm.inTransaction {
                if (addToBackStack) addToBackStack(fragment.javaClass.name)
                replace(R.id.container, fragment, fragment.javaClass.name)
            }
        }
    }

    internal fun backToRoot() {
        activity?.getSupportFragmentManager()?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    internal fun back() {
        activity?.getSupportFragmentManager()?.popBackStack()
    }

    inline fun <reified A : BaseActivity<*, *>> navigateToActivity(finished: Boolean = false) {
        `access$activity`?.let { act ->
            act.hideKeyboard()
            act.startActivity<A>()
            if (finished) act.finishAffinity()
        }
    }

    fun setFragmentManager(childFragmentManager: FragmentManager?) {
        fragmentManager = childFragmentManager
    }

    @PublishedApi
    internal val `access$activity`: BaseActivity<*, *>?
        get() = activity
}