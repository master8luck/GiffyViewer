package com.masterluck.giffyviewer

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Extensions {

    fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}