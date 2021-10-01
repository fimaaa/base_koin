package com.example.basekotlinkoin.base

import android.app.Application
import android.content.Context
import com.example.basekotlinkoin.di.*
import com.example.basekotlinkoin.di.localModule
import com.example.basekotlinkoin.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

const val CHANNEL_ID = "MyIndiHomeChannel"

class BaseApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    viewModelModule,
                    apiRepositoryModule,
                    remoteModule,
                    localModule,
                    apiModule,
                    prefsModule
                )
            )
        }
    }
}