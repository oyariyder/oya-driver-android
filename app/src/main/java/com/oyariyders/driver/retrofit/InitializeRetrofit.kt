package com.oyariyders.driver.retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object InitializeRetrofit {
    // Configure JSON serialization
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    // Create OkHttpClient with timeout configuration
    private val okHttpClient = OkHttpClient.Builder()
        // Configure timeouts
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)

        // Add logging for debugging (remove in production)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    val driverApi: DriverApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://yfbqbaqkmhtsqeknctye.supabase.co")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(DriverApi::class.java)
    }
}