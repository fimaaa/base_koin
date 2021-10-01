package com.example.basekotlinkoin.di

import com.example.basekotlinkoin.data.repository.ApiRepoUsersBasic
import com.example.basekotlinkoin.util.PrefManager
import org.koin.core.context.GlobalContext
import org.koin.core.qualifier.named

val prefs by lazy { GlobalContext.get().koin.get<PrefManager>() }
val apiKey by lazy { GlobalContext.get().koin.get<String>(named(API_KEY)) }
val webUrl by lazy { GlobalContext.get().koin.get<String>(named(WEB_URL)) }
val apiRepositoryUser by lazy { GlobalContext.get().koin.get<ApiRepoUsersBasic>() }

