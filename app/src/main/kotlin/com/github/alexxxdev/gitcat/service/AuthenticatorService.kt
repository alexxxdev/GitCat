package com.github.alexxxdev.gitcat.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.alexxxdev.gitcat.data.Authenticator
import org.koin.android.ext.android.inject

class AuthenticatorService : Service() {

    private val authenticator by inject<Authenticator>()

    override fun onBind(intent: Intent?): IBinder? {
        return authenticator.iBinder
    }
}