package com.oyariyders.driver.repository

import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.utils.Result
import com.oyariyders.driver.domain.model.OTP
import com.oyariyders.driver.domain.model.UserInfo
import com.oyariyders.driver.retrofit.DriverApi


class DriverRepository(val api: DriverApi) {
    suspend fun sendOtp(path: String): Result<OTP> {
        val response = try {
            api.sendOtp(path)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun verifyOtp(userInfo: UserInfo): Result<OTP> {
        val response = try {
            api.verifyOtp(userInfo)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun signUp(driver: Driver): Result<OTP> {
        val response = try {
            api.signUp(driver)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }

    suspend fun recoverAccount(emailAddress: String): Result<OTP> {
        val response = try {
            api.recoverAccount(emailAddress)
        }catch(e: Exception){
            return Result.Error(e.message ?: "Unknown error")
        }
        return Result.Success(response)
    }
}