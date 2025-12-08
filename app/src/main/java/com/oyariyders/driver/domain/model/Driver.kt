package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val email: String,
    val data: UserData
)