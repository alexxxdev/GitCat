package com.github.alexxxdev.gitcat.ui.auth.login

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ext.hideKeyboard
import com.github.alexxxdev.gitcat.ext.vectorDrawableToBitmap
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.codeED
import kotlinx.android.synthetic.main.fragment_login.loginConstrait
import kotlinx.android.synthetic.main.fragment_login.loginED
import kotlinx.android.synthetic.main.fragment_login.passED
import kotlinx.android.synthetic.main.fragment_login.sendButton

const val ANIM_DURATION_DONE = 200L
const val ANIM_DURATION_CHANGE_LAYOUT = 300L
const val ANIM_DURATION_START_CHANGE_LAYOUT = 900L
const val ANIM_DURATION_REVERT = 500L

class LoginFragment : BaseFragment<LoginContract.View, LoginPresenter>(), LoginContract.View {
    companion object {
        fun instance() = LoginFragment()
    }

    override val layoutId: Int = R.layout.fragment_login
    override fun providePresenter() = LoginPresenter()

    val color: Int by lazy { ContextCompat.getColor(context!!, R.color.colorAccent) }
    val decodeResource: Bitmap by lazy { context!!.resources.vectorDrawableToBitmap(context!!, R.drawable.ic_github_face) }

    val doneAnimationRunnable = {
        sendButton.doneLoadingAnimation(color, decodeResource)
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        sendButton.setOnClickListener { onClickLogin() }
        passED.setOnEditorActionListener(actionListener { onClickLogin() })
        codeED.setOnEditorActionListener(actionListener { onClickSend2FACode() })
    }

    override fun onSuccess() {
        with(Handler()) {
            postDelayed(doneAnimationRunnable, ANIM_DURATION_DONE)
            postDelayed({
                sendButton.revertAnimation()
                Snackbar.make(sendButton, "Success!", Snackbar.LENGTH_LONG).show()
            }, ANIM_DURATION_REVERT)
            postDelayed({ navigator.navigateToHome(true) }, ANIM_DURATION_REVERT)
        }
    }

    override fun onError(message: String) {
        with(Handler()) {
            postDelayed(doneAnimationRunnable, ANIM_DURATION_DONE)
            postDelayed({
                sendButton.revertAnimation()
                Log.v("FuelLogger", message)
                Snackbar.make(sendButton, message, Snackbar.LENGTH_LONG).show()
            }, ANIM_DURATION_REVERT)
        }
    }

    override fun onNeedCode2FA() {
        sendButton.setOnClickListener {}
        with(Handler()) {
            postDelayed(doneAnimationRunnable, ANIM_DURATION_DONE)
            postDelayed({
                sendButton.revertAnimation { sendButton.text = "Send code" }
            }, ANIM_DURATION_REVERT)
            postDelayed({
                val constraint1 = ConstraintSet()
                constraint1.clone(context, R.layout.fragment_login_code)

                val transition = AutoTransition()
                transition.duration = ANIM_DURATION_CHANGE_LAYOUT
                transition.interpolator = OvershootInterpolator()

                TransitionManager.beginDelayedTransition(loginConstrait, transition)
                constraint1.applyTo(loginConstrait)
                sendButton.setOnClickListener { onClickSend2FACode() }
            }, ANIM_DURATION_START_CHANGE_LAYOUT)
        }
    }

    private fun onClickLogin() {
        passED.clearFocus()
        passED.hideKeyboard()
        sendButton.startAnimation()
        presenter.login(loginED.text.toString(), passED.text.toString())
    }

    private fun onClickSend2FACode() {
        codeED.clearFocus()
        codeED.hideKeyboard()
        sendButton.startAnimation()
        presenter.send2FACode(codeED.text.toString())
    }

    private fun actionListener(function: () -> Unit): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    function()
                    return@OnEditorActionListener true
                }
            }
            return@OnEditorActionListener false
        }
    }
}