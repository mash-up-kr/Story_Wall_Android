package com.mashup.damgledamgle.network

import com.mashup.damgledamgle.data.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *  EnglishAddressServiceBuilder.kt
 *
 *  Created by Minji Jeong on 2022/09/25
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class EnglishAddressServiceBuilder @Inject constructor() {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val levelType = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(levelType)
    }

    private val timeout: Long = 60
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(timeout, TimeUnit.SECONDS)
            readTimeout(timeout, TimeUnit.SECONDS)
            writeTimeout(timeout, TimeUnit.SECONDS)
            addNetworkInterceptor(loggingInterceptor)
        }.build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://business.juso.go.kr/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    inline fun <reified T> buildService(): T {
        return retrofit.create(T::class.java)
    }
}
