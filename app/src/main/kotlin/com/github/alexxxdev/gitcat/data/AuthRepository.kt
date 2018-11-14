package com.github.alexxxdev.gitcat.data

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.data.model.auth.AuthorizationResponse
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

const val ACCOUNT_TYPE = "com.github.alexxxdev.gitcat"
const val ACCOUNT_PARAM1 = "com.github.alexxxdev.gitcat.hashed_token"
const val ACCOUNT_PARAM2 = "com.github.alexxxdev.gitcat.id"

const val ARG_ACCOUNT_TYPE = "ARG_ACCOUNT_TYPE"
const val ARG_AUTH_TYPE = "ARG_AUTH_TYPE"
const val ARG_IS_ADDING_NEW_ACCOUNT = "ARG_IS_ADDING_NEW_ACCOUNT"
const val ARG_ACCOUNT_PARAM1 = "ACCOUNT_PARAM1"
const val ARG_ACCOUNT_PARAM2 = "ACCOUNT_PARAM2"

class AuthRepository(val context: Context, val client: GithubAuthClient) {

    private var account: com.github.alexxxdev.gitcat.data.model.common.Account? = null
    var login: String? = null
    private var password: String? = null
    var user: User? = null

    fun login(login: String, password: String): Triple<Request, Response, Result<AuthorizationResponse, FuelError>> {
        this.login = login
        this.password = password

        val result = client.login(login, password)
        setToken(result, login)
        return result
    }

    fun send2FACode(code: String): Triple<Request, Response, Result<AuthorizationResponse, FuelError>> {
        val result = client.send2FACode(login, password, code)
        setToken(result, login)
        return result
    }

    private fun clear() {
        password = null
    }

    private fun setToken(result: Triple<Request, Response, Result<AuthorizationResponse, FuelError>>, login: String?) {
        if (result.second.statusCode == CODE_AUTH_SUCCESS) {
            val am = AccountManager.get(context)
            val account = Account(login?.trim(), ACCOUNT_TYPE)
            am.addAccountExplicitly(account, null, null)
            val token = result.third.get().token
            val hashedToken = result.third.get().hashedToken
            val id = result.third.get().id
            am.setAuthToken(account, account.type, token)
            am.setUserData(account, ACCOUNT_PARAM1, hashedToken)
            am.setUserData(account, ACCOUNT_PARAM2, id.toString())
            this.account = com.github.alexxxdev.gitcat.data.model.common.Account(id, token, hashedToken)
            clear()
        }
    }

    fun isLoggedIn(): Boolean {
        if (this.account != null) return true

        AccountManager.get(context)?.let { am ->
            am.getAccountsByType(ACCOUNT_TYPE).firstOrNull()?.let { account ->
                login = account.name.trim()
                val token = am.peekAuthToken(account, account.type)
                val hashedToken = am.getUserData(account, ACCOUNT_PARAM1)
                val id = am.getUserData(account, ACCOUNT_PARAM2)
                this.account = com.github.alexxxdev.gitcat.data.model.common.Account(id.toLong(), token, hashedToken)
                return true
            }
        }
        return false
    }

    fun logout() {
        this.account = null
        AccountManager.get(context)?.let { am ->
            am.getAccountsByType(ACCOUNT_TYPE).firstOrNull()?.also { account ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    am.removeAccountExplicitly(account)
                } else {
                    am.removeAccount(account, null, null)
                }
            }
        }
    }

    fun getToken(): String? = account?.token
}