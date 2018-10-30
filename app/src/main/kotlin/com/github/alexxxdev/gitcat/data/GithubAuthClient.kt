package com.github.alexxxdev.gitcat.data

import android.util.Log
import com.github.alexxxdev.gitcat.BuildConfig
import com.github.alexxxdev.gitcat.data.model.auth.AuthorizationRequest
import com.github.alexxxdev.gitcat.data.model.auth.AuthorizationResponse
import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import com.github.kittinunf.result.Result
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

class GithubAuthClient : KoinComponent {

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
    fun login(login: String, password: String): Triple<Request, Response, Result<AuthorizationResponse, FuelError>> {
        val responseObject = Fuel.post("/authorizations")
                .authenticate(login, password)
                .body(JSON.stringify(AuthorizationRequest("GitCat", listOf("user:email", "public_repo"))))
                .responseObject(kotlinxDeserializerOf<AuthorizationResponse>(json = JSON.nonstrict))
        Log.v("FuelLogger", responseObject.log())
        return responseObject
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun send2FACode(login: String?, password: String?, code: String): Triple<Request, Response, Result<AuthorizationResponse, FuelError>> {
        val responseObject = Fuel.post("/authorizations")
                .header(HEADER_OTP to code)
                .authenticate(login ?: "", password ?: "")
                .body(JSON.stringify(AuthorizationRequest("GitCat", listOf("user:email", "public_repo"))))
                .responseObject(kotlinxDeserializerOf<AuthorizationResponse>(json = JSON.nonstrict))
        Log.v("FuelLogger", responseObject.log())
        return responseObject
    }
}

@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> Triple<Request, Response, Result<T, FuelError>>.log(): String {
    var data: String = "(empty)"
    if (BuildConfig.DEBUG) {
        this.third.component2()?.errorData?.let { String(it) }?.let { data = JSON.indented.stringify(JSON.nonstrict.parse<Error>(it)) }
        this.third.component1()?.let { data = JSON.indented.stringify<T>(it) }
        return "--> ${this.first.httpString()}\n\n<-- ${this.second.statusCode} ${this.second.url}\n${this.second.responseMessage}\n$data"
    } else return ""
}
