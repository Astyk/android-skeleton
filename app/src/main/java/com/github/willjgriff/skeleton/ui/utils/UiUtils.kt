package com.github.willjgriff.skeleton.ui.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Will on 15/01/2017.
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi / 160f);
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi / 160f)
}

fun hideSoftKeyboard(view: View, context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
    if (inputMethodManager is InputMethodManager) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
