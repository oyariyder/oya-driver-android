package com.oyariyders.driver.retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object InitializeRetrofit {
    val driverApi: DriverApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://staging.oyariyders.com")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(DriverApi::class.java)
    }
}