package com.oyariyders.driver.retrofit

import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.domain.model.OTP
import com.oyariyders.driver.domain.model.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface DriverApi {
    @POST("v1/auth/send-otp/")
    suspend fun sendOtp(@Body path: String): OTP

    @POST("v1/auth/verify-otp/")
    suspend fun verifyOtp(@Body userInfo: UserInfo): OTP

    @POST("v1/auth/signup/")
    suspend fun signUp(@Body driver: Driver): OTP

    @POST("v1/auth/reset-password/")
    suspend fun recoverAccount(@Body emailAddress: String): OTP
}