package com.github.alexxxdev.gitcat.ext

import android.view.View
import org.jetbrains.anko.inputMethodManager

fun View.hideKeyboard() {
    context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}