package com.example.basekotlinkoin.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.basekotlinkoin.R
import com.example.basekotlinkoin.base.BaseApplication
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toast.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.support.v4.onScrollChange
import java.text.SimpleDateFormat
import java.util.*

const val Toast_Error = -1
const val Toast_Default = 0

@SuppressLint("InflateParams")
fun Context.showToast(
    message: CharSequence,
    typeToast: Int? = Toast_Default,
    isLong: Boolean? = true
) {
    val view = this.layoutInflater.inflate(R.layout.custom_toast, null)
    view.txtMessage.text = message
    when (typeToast) {
        Toast_Error -> {
            view.txtMessage.background = ContextCompat.getDrawable(this, R.drawable.bg_toast_error)
        }
        else -> {
            view.txtMessage.background = ContextCompat.getDrawable(this, R.drawable.bg_toast)
        }
    }

    val toast = Toast(this)
    toast.duration = if (isLong != false) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
    toast.view = view
    toast.show()
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun Activity.changeStatusBarColor(color: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(
        BaseApplication
            .applicationContext(), color
    )
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = this.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.showDateDialog(
    selectedDate: (date: String) -> Unit
) {
    val mCalendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        this, R.style.DialogTheme,
        { _, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            // val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(mCalendar.time)
            val date = SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'", Locale.getDefault()).format(
                mCalendar.time
            )
            selectedDate(date)
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    datePickerDialog.show()
}

fun Activity.scrollBehaviour(scroll: NestedScrollView) {
    scroll.onScrollChange { _, _, scrollY, _, _ ->
        if (scrollY != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(
                BaseApplication
                    .applicationContext(), R.color.colorWhite
            )
        } else if (scrollY == 0) makeStatusBarTransparent()
    }
}

fun Activity.makeDefaultStatusBar() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(
        BaseApplication
            .applicationContext(), R.color.colorWhite
    )
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
}

fun Context.openLinkApps(url: String) {
    val packageName = url.substringAfter("=")
    var intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.let {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } ?: let {
        intent = Intent(Intent.ACTION_VIEW)
        intent?.data = Uri.parse(url)
        startActivity(intent)
    }
}

fun Context.convertTODP(size: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (size * scale + 0.5f).toInt()
}

/**
 * @param nestedScrollView instantiation of NestedScrollView
 */
fun Fragment.setScrollBehaviour(nestedScrollView: NestedScrollView) {
    (requireActivity() as AppCompatActivity).setScrollBehaviour(nestedScrollView)
}

/**
 * @param nestedScrollView instantiation of NestedScrollView
 */
fun AppCompatActivity.setScrollBehaviour(nestedScrollView: NestedScrollView) {
    scrollBehaviour(nestedScrollView)
    nestedScrollView.onScrollChange { _, _, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
            window.apply {
                statusBarColor = Color.WHITE
            }
            if (appbar != null) {
                val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
                params.setMargins(0, 0, 0, 0)
                appbar.layoutParams = params
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                toolbar?.animate()?.translationY(-toolbar.bottom.toFloat())?.setInterpolator(
                    AccelerateInterpolator()
                )?.start()
                shadow_statusbar.layoutParams.height = getStatusBarHeight()
                shadow_statusbar.requestLayout()
            }
        } else if (scrollY < oldScrollY) {
            window.apply {
                statusBarColor = Color.WHITE
            }
            if (appbar != null) {
                val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
                params.setMargins(0, 0, 0, 0)
                appbar.layoutParams = params
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                toolbar?.animate()?.translationY(0f)?.setInterpolator(DecelerateInterpolator())
                    ?.start()
                shadow_statusbar.layoutParams.height = getStatusBarHeight()
                shadow_statusbar.requestLayout()
            }
        }

        if (scrollY == 0) {
            actionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(128, 0, 0, 0)))
            makeStatusBarTransparent()
            if (appbar != null) {
                val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
                params.setMargins(0, getStatusBarHeight(), 0, 0)
                appbar.layoutParams = params
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                shadow_statusbar.layoutParams.height = 0
                shadow_statusbar.requestLayout()
            }
        }
    }
}

fun Context.openDialer(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}

fun Context.versionAppName(): String {
    var versionAppName = ""
    versionAppName = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        ""
    }
    return versionAppName
}