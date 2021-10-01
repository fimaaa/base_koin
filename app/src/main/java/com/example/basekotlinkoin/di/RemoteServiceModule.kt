package com.example.basekotlinkoin.di

import com.example.basekotlinkoin.data.source.remote.*
import com.example.basekotlinkoin.di.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASIC_AUTH_TYPE = "basic_auth"
const val BEARER_AUTH_TYPE = "bearer_auth"

val remoteModule = module {
    single { provideCacheInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single(named(BASIC_AUTH_TYPE)) { provideOkHttpClientBasic(get(named(BASIC_AUTH))) }
    single(named(BEARER_AUTH_TYPE)) { provideOkHttpClientJwt() }
    single {
        provideClientUserBasic(
            get(named(BASIC_AUTH_TYPE)),
            get(named(BASE_URL_USER)),
            get()
        )
    }
}
