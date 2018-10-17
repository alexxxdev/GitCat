package com.github.alexxxdev.gitcat.ui.base

import android.app.Activity
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.*
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState

abstract class BaseFragment<V : MvpView, P : MvpPresenter<V>, VS : ViewState<V>> : Fragment(), MvpViewStateDelegateCallback<V, P, VS>, MvpView {
    abstract val layoutId: Int
    abstract override fun createViewState(): VS
    abstract override fun createPresenter(): P

    private lateinit var viewState: VS
    private lateinit var presenter: P

    private var mvpDelegate: FragmentMvpDelegate<V, P> = FragmentMvpViewStateDelegateImpl(this, this, true, true)

    private var restoringViewState = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(layoutId, container, false)
        return fragmentView
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate.onActivityCreated(savedInstanceState)
    }

    @CallSuper
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mvpDelegate.onAttach(activity)
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        mvpDelegate.onDetach()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun setRestoringViewState(restoringViewState: Boolean) {
        this.restoringViewState = restoringViewState
    }

    override fun isRestoringViewState(): Boolean {
        return restoringViewState
    }

    override fun onViewStateInstanceRestored(instanceStateRetained: Boolean) = Unit

    override fun setPresenter(presenter: P) {
        this.presenter = presenter
    }

    override fun setViewState(viewState: VS) {
        this.viewState = viewState
    }

    override fun getMvpView(): V = this as V

    override fun getViewState(): VS = viewState

    override fun getPresenter(): P = presenter

    override fun onNewViewStateInstance() {
    }
}