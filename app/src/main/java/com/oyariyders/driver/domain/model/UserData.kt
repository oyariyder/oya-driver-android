package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData (
    val firstname: String,
    val phone: String,
)