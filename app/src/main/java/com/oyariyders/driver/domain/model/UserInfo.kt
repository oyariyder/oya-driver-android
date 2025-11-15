package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val path: String,
    val otp: String
)