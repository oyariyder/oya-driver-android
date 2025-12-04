package com.oyariyders.driver.retrofit

import com.oyariyders.driver.domain.model.AuthResponse
import com.oyariyders.driver.domain.model.BioResponse
import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.domain.model.EmailRequest
import com.oyariyders.driver.domain.model.OTP
import com.oyariyders.driver.domain.model.PhoneRequest
import com.oyariyders.driver.domain.model.UserInfo
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface DriverApi {
    @POST("/auth/v1/otp")
    suspend fun sendOtp(
        @Header("apiKey") apiKey: String,
        @Body phone: PhoneRequest
    )
    @POST("/auth/v1/magiclink")
    suspend fun sendEmailOtp(
        @Header("apiKey") apiKey: String,
        @Body email: EmailRequest
    )
    @POST("/auth/v1/verify")
    suspend fun verifyOtp(
        @Header("apiKey") apiKey: String,
        @Body userInfo: UserInfo
    ): AuthResponse

    @PUT("/auth/v1/user")
    suspend fun signUp(
        @Header("apiKey") apiKey: String,
        @Header("Authorization") token: String,
        @Body driver: Driver
    ): BioResponse

    @POST("/auth/v1/reset-password")
    suspend fun recoverAccount(
        @Header("apiKey") apiKey: String,
        @Body emailAddress: EmailRequest
    )
}