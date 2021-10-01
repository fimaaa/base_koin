package com.example.basekotlinkoin.di

import com.example.basekotlinkoin.ui.splash.SplashViewModel
import com.example.basekotlinkoin.util.ContextProviders
import com.example.basekotlinkoin.util.PrefManager
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(androidApplication()) }
}

val apiRepositoryModule = module {
    single { ContextProviders.getInstance() }
//    single { ApiRepoNpsBasic(get()) }
}

val prefsModule = module {
    single { PrefManager() }
}
