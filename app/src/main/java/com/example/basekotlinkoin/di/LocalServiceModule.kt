package com.example.basekotlinkoin.di

import com.example.basekotlinkoin.data.source.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single { AppDatabase.getDatabase(androidApplication()) }
}