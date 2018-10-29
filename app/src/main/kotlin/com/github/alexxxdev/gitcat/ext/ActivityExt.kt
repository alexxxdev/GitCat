package com.github.alexxxdev.gitcat.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    this.getSystemService(Activity.INPUT_METHOD_SERVICE).let { service ->
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        (service as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }
}