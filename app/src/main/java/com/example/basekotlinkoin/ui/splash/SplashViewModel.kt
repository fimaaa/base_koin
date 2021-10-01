package com.example.basekotlinkoin.ui.splash

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.basekotlinkoin.base.BaseViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class SplashViewModel(application: Application) : BaseViewModel<SplashNavigator>(application) {

    val delay = 2000L

    fun displaySplashAsync(): Deferred<Boolean> {
        return viewModelScope.async {
            delay(delay)
            return@async true
        }
    }
}
