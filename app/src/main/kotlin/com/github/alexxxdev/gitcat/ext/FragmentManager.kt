package com.github.alexxxdev.gitcat.ext

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        block()
        commit()
    }
}