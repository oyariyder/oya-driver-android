package com.oyariyders.driver.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EmailRequest (
    val email: String
)