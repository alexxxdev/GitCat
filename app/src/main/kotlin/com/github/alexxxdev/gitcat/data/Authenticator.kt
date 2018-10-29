package com.github.alexxxdev.gitcat.data

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.github.alexxxdev.gitcat.ui.auth.AuthActivity
import org.jetbrains.anko.intentFor

class Authenticator(val context: Context) : AbstractAccountAuthenticator(context) {
    override fun getAuthTokenLabel(authTokenType: String?): String = "$authTokenType (Label)"

    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
        val am = AccountManager.get(context)
        val authToken: String? = am.peekAuthToken(account, authTokenType)
        if (authToken.isNullOrBlank()) {
            return bundleOf()
        } else {
            return bundleOf(
                    AccountManager.KEY_ACCOUNT_NAME to account?.name,
                    AccountManager.KEY_ACCOUNT_TYPE to account?.type,
                    AccountManager.KEY_AUTHTOKEN to authToken,
                    ARG_ACCOUNT_PARAM1 to am.getUserData(account, ACCOUNT_PARAM1),
                    ARG_ACCOUNT_PARAM2 to am.getUserData(account, ACCOUNT_PARAM2)
            )
        }
    }

    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        val intent = context.intentFor<AuthActivity>(
                ARG_ACCOUNT_TYPE to accountType,
                ARG_AUTH_TYPE to authTokenType,
                ARG_IS_ADDING_NEW_ACCOUNT to true,
                AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE to response
        )
        return bundleOf(AccountManager.KEY_INTENT to intent)
    }

    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle = bundleOf()

    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle = bundleOf()

    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle = bundleOf()

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle = bundleOf()
}