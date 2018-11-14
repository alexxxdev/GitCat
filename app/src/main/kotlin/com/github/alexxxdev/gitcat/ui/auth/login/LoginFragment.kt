package com.github.alexxxdev.gitcat.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.github.alexxxdev.gitcat.R
import com.github.alexxxdev.gitcat.ext.hideKeyboard
import com.github.alexxxdev.gitcat.ui.base.BaseFragment
import com.github.alexxxdev.rotadilavk.Validator
import com.github.alexxxdev.rotadilavk.androidx.field.Field
import com.github.alexxxdev.rotadilavk.rule.RequiredRule
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.codeED
import kotlinx.android.synthetic.main.fragment_login.codeInputLayout
import kotlinx.android.synthetic.main.fragment_login.loginConstrait
import kotlinx.android.synthetic.main.fragment_login.loginED
import kotlinx.android.synthetic.main.fragment_login.loginInputLayout
import kotlinx.android.synthetic.main.fragment_login.passED
import kotlinx.android.synthetic.main.fragment_login.passInputLayout
import kotlinx.android.synthetic.main.fragment_login.sendButton

const val ANIM_DURATION_CHANGE_STATE = 700L
const val ANIM_DURATION_CHANGE_LAYOUT = 300L
const val ANIM_DURATION_START_CHANGE_LAYOUT = 900L

class LoginFragment : BaseFragment<LoginContract.View, LoginPresenter>(), LoginContract.View {
    companion object {
        fun instance() = LoginFragment()
    }

    override val layoutId: Int = R.layout.fragment_login
    override fun providePresenter() = LoginPresenter()

    val validatorLogin: Validator by lazy {
        val requiredRule = RequiredRule(getString(R.string.message_field_is_required))
        Validator(
                Field(loginInputLayout, requiredRule),
                Field(passInputLayout, requiredRule)
        ).enableTrim(true)
    }

    val validator2FA: Validator by lazy {
        val requiredRule = RequiredRule(getString(R.string.message_field_is_required))
        Validator(
                Field(codeInputLayout, requiredRule)
        ).enableTrim(true)
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        sendButton.setMyButtonClickListener { onClickLogin() }
        passED.setOnEditorActionListener(actionListener { onClickLogin() })
        codeED.setOnEditorActionListener(actionListener { onClickSend2FACode() })
    }

    override fun onSuccess() {
        sendButton.showDoneButton()
        sendButton.postDelayed({ presenter.gotoHome() }, ANIM_DURATION_CHANGE_STATE)
    }

    override fun onError(message: String) {
        sendButton.showErrorButton()
        Log.v("FuelLogger", message)
        Snackbar.make(sendButton, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onNeedCode2FA() {
        sendButton.setButtonLabel(getString(R.string.btn_login2fa))
        sendButton.setMyButtonClickListener {}
        sendButton.showDoneButton()
        sendButton.postDelayed({ sendButton.showNormalButton() }, ANIM_DURATION_CHANGE_STATE)
        sendButton.postDelayed({
            val constraint1 = ConstraintSet()
            constraint1.clone(context, R.layout.fragment_login_code)

            val transition = AutoTransition()
            transition.duration = ANIM_DURATION_CHANGE_LAYOUT
            transition.interpolator = OvershootInterpolator()

            TransitionManager.beginDelayedTransition(loginConstrait, transition)
            constraint1.applyTo(loginConstrait)
            sendButton.setMyButtonClickListener { onClickSend2FACode() }
        }, ANIM_DURATION_START_CHANGE_LAYOUT)
    }

    private fun onClickLogin() {
        if (validatorLogin.validate()) {
            passED.clearFocus()
            passED.hideKeyboard()
            sendButton.showLoadingButton()
            presenter.login(loginED.text.toString(), passED.text.toString())
        } else {
            sendButton.showNormalButton()
        }
    }

    private fun onClickSend2FACode() {
        if (validator2FA.validate()) {
            codeED.clearFocus()
            codeED.hideKeyboard()
            sendButton.showLoadingButton()
            presenter.send2FACode(codeED.text.toString())
        } else {
            sendButton.showNormalButton()
        }
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