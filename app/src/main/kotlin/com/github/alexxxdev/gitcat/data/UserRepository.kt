package com.github.alexxxdev.gitcat.data

import android.util.Log
import com.github.alexxxdev.fuelcomfy.setInterface
import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.rest.Event
import com.github.alexxxdev.gitcat.data.model.rest.Notification
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.cUrlString

class UserRepository(
    private val authRepository: AuthRepository
) {

    var token: String
    val service: GithubService

    init {
        token = authRepository.getToken().orEmpty()
        FuelManager.instance.basePath = "https://api.github.com"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")

        FuelManager.instance.addResponseInterceptor { next: (Request, Response) -> Response ->
            { req: Request, res: Response ->
                Log.d("FuelManager", req.cUrlString())
                Log.d("FuelManager", String(res.data))
                next(req, res)
            }
        }

        service = FuelManager.instance.setInterface(GithubService::class)
    }

    suspend fun getUserEvent(username: String, page: Int = 1): Result<List<Event>> {
        val responseObject = service.getUserEvent(username, page, token)

        return try {
            if (responseObject.component1() != null) {
                Result.of(responseObject.component1())
            } else {
                Result.error(Error.of((responseObject.component2() as FuelError).response.statusCode, responseObject.component2() as? FuelError))
            }
        } catch (ex: Exception) {
            Result.error(Error.of(ex))
        }
    }

    suspend fun getUserNotifications(page: Int = 1, all: Boolean = false): Result<List<Notification>> {
        val responseObject = service.getUserNotification(page, all, token)

        return try {
            if (responseObject.component1() != null) {
                Result.of(responseObject.component1())
            } else {
                Result.error(Error.of((responseObject.component2() as FuelError).response.statusCode, responseObject.component2() as? FuelError))
            }
        } catch (ex: Exception) {
            Result.error(Error.of(ex))
        }
    }
}