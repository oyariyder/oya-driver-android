package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PhoneRequest (
    val phone: String
)