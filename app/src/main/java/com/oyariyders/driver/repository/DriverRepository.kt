package com.oyariyders.driver.repository

import com.oyariyders.driver.domain.model.AuthResponse
import com.oyariyders.driver.domain.model.BioResponse
import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.domain.model.EmailRequest
import com.oyariyders.driver.utils.Result
import com.oyariyders.driver.domain.model.OTP
import com.oyariyders.driver.domain.model.PhoneRequest
import com.oyariyders.driver.domain.model.UserInfo
import com.oyariyders.driver.retrofit.DriverApi


class DriverRepository(val api: DriverApi) {
    val apiKey = "sb_secret_cKc-dtiOw_g61teXNFKu2w_FPOWM5aG"
    suspend fun sendOtp(path: String): Result<Unit> {
        val phone = path.removePrefix("+")
       val phoneReq = PhoneRequest(phone)
        val response = try {
            api.sendOtp(apiKey, phoneReq)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun sendEmailOtp(mail: String): Result<Unit> {
        val email = EmailRequest(mail)
        val response = try {
            api.sendEmailOtp(apiKey, email)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun verifyOtp(userInfo: UserInfo): Result<AuthResponse> {
        val response = try {
            api.verifyOtp(apiKey, userInfo)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun signUp(driver: Driver, token: String): Result<BioResponse> {
        val response = try {
            api.signUp(
                apiKey,
                token = "Bearer $token",
                driver)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun recoverAccount(emailAddress: String): Result<Unit> {
        val email = EmailRequest(emailAddress)
        val response = try {
            api.recoverAccount(apiKey, email)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }
}