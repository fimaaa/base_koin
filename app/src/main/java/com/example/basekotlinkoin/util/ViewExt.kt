package com.example.basekotlinkoin.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import org.jetbrains.anko.sdk27.coroutines.onScrollChange

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Context?.showKeyboard() {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun Context?.hideKeyboard(view: View?) {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        view?.windowToken,
        0
    )
}

fun Context.setBackground(drawable: Int): Drawable? = ContextCompat.getDrawable(this, drawable)

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun NestedScrollView.onScroll(
    listener: (v: View?, scrollY: Int, alpha: Float) -> Unit
) {
    onScrollChange { v, _, scrollY, _, _ ->
        listener.invoke(v, scrollY, scrollY.toFloat() / 500)
    }
}