package com.example.basekotlinkoin.di

import com.example.basekotlinkoin.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val WEB_URL: String = "WEB_URL"
const val BASIC_AUTH: String = "BASIC_AUTH"
const val API_KEY: String = "API_KEY"
const val BASE_URL_USER: String = "BASE_URL_USER"

val apiModule = module {

    object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    single(named(WEB_URL)) { getBaseUrlWeb() }
    single(named(BASIC_AUTH)) {
        //TODO WITH BASIC TOKEN
        ""
    }
    single(named(API_KEY)) {
        // TODO WITH API KEY
        ""
    }
    single(named(BASE_URL_USER)) { getBaseUrlUser() }
}


external fun getDevelopmentWebUrl(): String
external fun getDevUrl(): String
external fun getStagingUrl(): String
external fun getReleaseUrl(): String

private fun getBaseUrlWeb(): String =
    when (BuildConfig.VARIANT) {
        "development" -> getDevelopmentWebUrl()
        "release" -> getDevelopmentWebUrl()
        "staging" -> getDevelopmentWebUrl()
        else -> getDevelopmentWebUrl()
    }

private fun getBaseUrlUser(): String =
    when (BuildConfig.VARIANT) {
        "development" -> getDevUrl()
        "release" -> getReleaseUrl()
        "staging" -> getStagingUrl()
        else -> getDevUrl()
    }