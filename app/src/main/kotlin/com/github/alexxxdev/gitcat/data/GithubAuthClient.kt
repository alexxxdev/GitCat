package com.github.alexxxdev.gitcat.data

import android.util.Log
import awaitObjectResponse
import com.github.alexxxdev.gitcat.BuildConfig
import com.github.alexxxdev.gitcat.data.model.auth.AuthorizationRequest
import com.github.alexxxdev.gitcat.data.model.auth.AuthorizationResponse
import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.authenticate
import com.github.kittinunf.fuel.core.extensions.httpString
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.koin.standalone.KoinComponent

const val HEADER_OTP = "X-GitHub-OTP"
const val REQUIRED_SMS = "required; sms"
const val REQUIRED_APP = "required; app"
const val CODE_AUTH_ERROR = 401
const val CODE_AUTH_SUCCESS = 201
const val CODE_SUCCESS = 200

class Need2FAException(message: String) : Exception(message)

class GithubAuthClient : KoinComponent {

    private val authRequest = AuthorizationRequest("GitCat", listOf("user:email", "public_repo", "read:org", "notifications", "repo"))

    private fun loggerInterceptor() =
            { next: (Request, Response) -> Response ->
                { req: Request, res: Response ->
                    Log.v("FuelLogger", req.toString())
                    Log.v("FuelLogger", res.toString())
                    next(req, res)
                }
            }

    init {
        FuelManager.instance.basePath = "https://api.github.com"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        // FuelManager.instance.addResponseInterceptor(loggerInterceptor())
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    suspend fun login(login: String, password: String): Result<AuthorizationResponse> {
        val responseObject = Fuel.post("/authorizations")
                .authenticate(login, password)
                .body(JSON.stringify(authRequest))
                .awaitObjectResponse(kotlinxDeserializerOf<AuthorizationResponse>(json = JSON.nonstrict))

        return if (responseObject.second.statusCode == CODE_AUTH_SUCCESS) {
            Result.of(responseObject.third.component1())
        } else {
            val fuelError = responseObject.third.component2()
            if (fuelError?.response?.statusCode == CODE_AUTH_ERROR && fuelError.response.headers.containsKey(HEADER_OTP)) {
                if (fuelError.response.headers.getValue(HEADER_OTP).find { str -> str == REQUIRED_SMS || str == REQUIRED_APP } != null) {
                    return Result.error(Error.of(responseObject.second.statusCode, Need2FAException("Must specify two-factor authentication OTP code.")))
                }
            }
            Result.error(Error.of(responseObject.second.statusCode, fuelError))
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    suspend fun send2FACode(login: String?, password: String?, code: String): Result<AuthorizationResponse> {
        val responseObject = Fuel.post("/authorizations")
                .header(HEADER_OTP to code)
                .authenticate(login ?: "", password ?: "")
                .body(JSON.stringify(authRequest))
                .awaitObjectResponse(kotlinxDeserializerOf<AuthorizationResponse>(json = JSON.nonstrict))

        return if (responseObject.second.statusCode == CODE_AUTH_SUCCESS) {
            Result.of(responseObject.third.component1())
        } else {
            Result.error(Error.of(responseObject.second.statusCode, responseObject.third.component2()))
        }
    }
}

@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> Triple<Request, Response, com.github.kittinunf.result.Result<T, FuelError>>.log(): String {
    var data: String = "(empty)"
    if (BuildConfig.DEBUG) {
        this.third.component2()?.errorData?.let { String(it) }?.let { data = JSON.indented.stringify(JSON.nonstrict.parse<Error>(it)) }
        // this.third.component1()?.let { data = JSON.indented.stringify<T>(it) }
        return "--> ${this.first.httpString()}\n\n<-- ${this.second.statusCode} ${this.second.url}\n${this.second.responseMessage}\n$data"
    } else return ""
}
