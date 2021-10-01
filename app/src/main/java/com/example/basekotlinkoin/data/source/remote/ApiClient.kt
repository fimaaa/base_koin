package com.example.basekotlinkoin.data.source.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.basekotlinkoin.base.BaseApplication
import com.example.basekotlinkoin.data.source.endpoint.ApiServiceUserBasic
import com.example.basekotlinkoin.di.apiRepositoryUser
import com.example.basekotlinkoin.di.prefs
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

fun provideCacheInterceptor() = run {
    Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge =
            60 // read from cache for 60 seconds even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
}

fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}

fun provideOkHttpClientBasic(basicAuth: String) = run {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(provideHttpLoggingInterceptor())
        .addInterceptor(provideCacheInterceptor())
        .addInterceptor(ChuckerInterceptor(BaseApplication.applicationContext()))
        .addInterceptor { chain ->
            val language = if (Locale.getDefault().language == "in") "id" else "en"
            val request = chain.request()
            val requestBuilder = request.newBuilder()
                .addHeader(
                    "Authorization", basicAuth
                )
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Language", language)
                .build()
            chain.proceed(requestBuilder)
        }
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
    okHttpClientBuilder.build()
}

fun provideOkHttpClientJwt() = run {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(provideHttpLoggingInterceptor())
        .addInterceptor(provideCacheInterceptor())
        .addInterceptor(ChuckerInterceptor(BaseApplication.applicationContext()))
        .authenticator(object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                val language = if (Locale.getDefault().language == "in") "id" else "en"
                var previousRetryCount = response.request.header("xObidanRetryCount")?.toInt() ?: 0
                previousRetryCount++

                if (previousRetryCount > 1)
                    return null
                else {
                    synchronized(this) {
                        runBlocking {
                            try {
                                val responseApi =
                                    apiRepositoryUser.getRefreshToken(prefs.prefRefreshToken)
                                val token = responseApi.data.accessToken
                                val refreshToken = responseApi.data.refreshToken
                                if (token != null && refreshToken != null) {
                                    prefs.prefUserToken = token
                                    prefs.prefRefreshToken = refreshToken
                                }
                            } catch (t: Throwable) {
                                // TODO : Error Handling
                            }
                        }
                        // return request with new token
                        response.close()
                        return response.request.newBuilder()
                            .header("Authorization", "Bearer ${prefs.prefUserToken}")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept-Language", language)
                            .header(
                                "xObidanRetryCount",
                                "$previousRetryCount"
                            )
                            .build()
                    }
                }
            }
        })
        .addInterceptor { chain ->
            val language = if (Locale.getDefault().language == "in") "id" else "en"
            val request = chain.request()
            val requestBuilder: Request
            requestBuilder = if (prefs.prefUserToken != null) {
                request.newBuilder()
                    .addHeader(
                        "Authorization", "Bearer ${prefs.prefUserToken}"
                    )
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .build()
            } else {
                request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Language", language)
                    .build()
            }
            val response = chain.proceed(requestBuilder)
            // TODO : Handle when refresh token is expired
//            if (response.code == 403) {
//                MyIhApp.applicationContext().sendBroadcast(Intent(ACTION_INVALID_TOKEN))
//            }
            response
        }
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
    okHttpClientBuilder.build()
}

fun provideClientUserBasic(
    okHttpClient: OkHttpClient,
    baseUrl: String,
    moshiConverter: Moshi
): ApiServiceUserBasic {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiServiceUserBasic::class.java)
}
