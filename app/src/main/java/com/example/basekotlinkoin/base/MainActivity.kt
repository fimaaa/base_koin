package com.example.basekotlinkoin.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.basekotlinkoin.R
import com.example.basekotlinkoin.customview.CustomScrollView
import com.example.basekotlinkoin.util.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onScrollChange

class MainActivity : AppCompatActivity(), MainView {

    private var backNav: Int = R.drawable.ic_icon_ic_back_red
    private var fragmentMenu: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        setContentView(R.layout.activity_main)
        initHeader(toolbar, appbar, false)
        scrollable(scroll, toolbar, appbar)
    }

    override fun initDefaultToolbar(title: String, invalidNav: Boolean) {
        supportActionBar?.title = title
        fragmentMenu = 0
        if (invalidNav) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
            toolbar.setContentInsetsRelative(42, 0)
        } else {
            toolbar.contentInsetStartWithNavigation = 0
            toolbar.setContentInsetsRelative(0, 0)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icon_ic_back_red)
            backNav = R.drawable.ic_icon_ic_back_red
        }
    }

    override fun initCustomToolbar(title: String, menu: Int?, navIcon: Int?) {
        supportActionBar?.title = title
        toolbar.contentInsetStartWithNavigation = 0
        toolbar.setContentInsetsRelative(0, 0)

        if (menu != null) {
            fragmentMenu = menu
        }

        if (navIcon != null) {
            toolbar.contentInsetStartWithNavigation = 0
            toolbar.setContentInsetsRelative(0, 0)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(navIcon)
            backNav = navIcon
        } else {
            toolbar.setContentInsetsRelative(42, 0)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }

    override fun toolbarState(state: Boolean) {
        if (state) {
            toolbar.visible()
        } else {
            toolbar.gone()
        }
    }

    override fun getToolbar(): MaterialToolbar = toolbar

    override fun setEnableScrollview(status: Boolean) {
        scroll.isEnableScrolling = status
    }

    override fun setActionToolbar(toolbar: Toolbar) {
        initHeader(toolbar, appbar, true)
    }

    override fun recycleToolbar() {
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(128, 0, 0, 0)))
        makeStatusBarTransparent()
    }

    override fun resetToolbar() {
        initHeader(toolbar, appbar, false)
    }

    override fun staticToolbar(title: String) {
        toolbar_title.text = title
        window.apply {
            statusBarColor = Color.WHITE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        appbar.layoutParams = params
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    override fun invalidToolbar() {
        toolbar_title.text = null
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(128, 0, 0, 0)))
        makeStatusBarTransparent()
        val params = appbar.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, getStatusBarHeight(), 0, 0)
        appbar.layoutParams = params
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun invalidScroll() {
        scroll.requestDisallowInterceptTouchEvent(true)
    }

    override fun backToScrolling() {
        scroll.requestDisallowInterceptTouchEvent(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (backNav == R.drawable.ic_icon_ic_back_red || backNav == R.drawable.ic_icon_ic_close_red || backNav == R.drawable.ic_icon_ic_close_white) {
                    super.onBackPressed()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (fragmentMenu != 0) {
            menuInflater.inflate(fragmentMenu, menu)
        }
        return true
    }

    /**
     * @author Myihx Codebase.
     * Scrolling behaviour
     */

    private fun initHeader(toolbar: Toolbar?, appbar: AppBarLayout?, isActionToolbar: Boolean) {
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(128, 0, 0, 0)))
        makeStatusBarTransparent()

        appbar?.apply {
            setSupportActionBar(toolbar)
            title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icon_ic_back_red)

            val params = layoutParams as CoordinatorLayout.LayoutParams
            params.setMargins(0, getStatusBarHeight(), 0, 0)
            layoutParams = params
            if (isActionToolbar) supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            else supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun scrollable(
        nestedScroll: CustomScrollView,
        toolbar: Toolbar?,
        appbar: AppBarLayout?
    ) {
        nestedScroll.onScrollChange { _, _, scrollY, _, oldScrollY ->
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
}