package com.github.alexxxdev.gitcat.ui.base

import com.github.alexxxdev.gitcat.R
import androidx.fragment.app.FragmentManager
import org.jetbrains.anko.startActivity

open class BaseNavigator {
    protected var activity: BaseActivity<*, *>? = null

    fun setContext(ctx: BaseActivity<*, *>) {
        activity = ctx
    }

    internal fun navigateToFragment(fragment: BaseFragment<*, *>, addToBackStack: Boolean) {
        activity?.let { act ->
            val fragmentTransaction = act.getSupportFragmentManager().beginTransaction()
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
            }

            fragmentTransaction.replace(R.id.container, fragment, fragment.javaClass.name)
            fragmentTransaction.commit()
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
            act.startActivity<A>()
            if (finished) act.finishAffinity()
        }
    }

    @PublishedApi
    internal val `access$activity`: BaseActivity<*, *>?
        get() = activity
}