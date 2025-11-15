package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val emailAddress: String,
)